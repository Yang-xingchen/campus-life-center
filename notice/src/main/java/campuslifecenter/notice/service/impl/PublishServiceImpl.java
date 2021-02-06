package campuslifecenter.notice.service.impl;

import brave.ScopedSpan;
import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.ProcessException;
import campuslifecenter.common.model.Response;
import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.mapper.*;
import campuslifecenter.notice.model.*;
import campuslifecenter.notice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static campuslifecenter.common.exception.ProcessException.*;
import static campuslifecenter.notice.model.NoticeConst.*;

@Service
public class PublishServiceImpl implements PublishService {

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private NoticeInfoMapper infoMapper;
    @Autowired
    private AccountNoticeMapper accountNoticeMapper;
    @Autowired
    private PublishTodoMapper publishTodoMapper;
    @Autowired
    private PublishInfoMapper publishInfoMapper;
    @Autowired
    private PublishOrganizationMapper publishOrganizationMapper;

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TodoService todoService;
    @Autowired
    private InformationService informationService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrganizationSubscribeService organizationSubscribeService;
    @Autowired
    private CacheService cacheService;

    @Value("${notice.redis.publish-notice}")
    private String PUBLISH_PREFIX;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private TracerUtil tracerUtil;

    @Override
    public String getPublishId(String token) {
        String aid = cacheService.getAccountIdByToken(token);
        String pid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(PUBLISH_PREFIX + pid, aid, 1, TimeUnit.DAYS);
        return pid;
    }

    @Override
    @NewSpan("get publish creator id")
    public String getPublishAid(String pid) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(PUBLISH_PREFIX + pid)).orElseGet(() -> {
            NoticeExample example = new NoticeExample();
            example.createCriteria().andFileRefEqualTo(pid);
            return noticeMapper.selectByExample(example).get(0).getCreator();
        });
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Long publicNotice(PublishNotice publishNotice) {
        return tracerUtil.newSpan("public notice", span -> {
            Notice notice = publishNotice.getNotice();
            tracerUtil.newSpan("check permission", scopedSpan -> {
                if (ORGANIZATION_SELF == notice.getOrganization()) {
                    notice.setPublishStatus(STATUS_PUBLISHING);
                    return;
                }
                List<PermissionService.Permission> permissions = permissionService
                        .getPermission(notice.getCreator(), notice.getOrganization())
                        .checkGet(USER_CENTER, "get permission fail");
                int max = permissions.stream()
                        .filter(permission -> permission.getType() == NOTICE_PERMISSION)
                        .mapToInt(permission -> {
                            try {
                                return Integer.parseInt(permission.getName().split(":")[1]);
                            } catch (RuntimeException e) {
                                e.printStackTrace();
                                return -1;
                            }
                        })
                        .max()
                        .orElse(-1);
                notice.setPublishStatus(max >= notice.getImportance() ? STATUS_PUBLISHING : STATUS_WAIT);
            });
            // 如果需要等待审核，则不发布到成员
            // TODO 会遗失单独发布成员
            if (notice.getPublishStatus() == STATUS_WAIT) {
                publishNotice.setAccountList(List.of());
            }
            tracerUtil.newSpan("init", scopedSpan -> {
                notice.setVersion(1);
                notice.setCreateTime(new Date());
                notice.setFileRef(publishNotice.getPid());
            });
            // 待办信息
            tracerUtil.newSpan("insert todo", scopedSpan -> {
                Response<String> response = todoService.add(new TodoService.AddTodoRequest()
                        .setAids(publishNotice.getAccountList())
                        .setValues(publishNotice.getTodo()));
                notice.setTodoRef(response.checkGet(TODO, "insert todo fail"));
            });
            // 通知
            tracerUtil.newSpan("insert notice", (Consumer<ScopedSpan>) scopedSpan -> noticeMapper.insertSelective(notice));
            // 标签
            tracerUtil.newSpan("insert tag", scopedSpan -> {
                tagService.addTag(publishNotice.getTag(), notice.getId());
            });
            // 信息收集
            tracerUtil.newSpan("insert info collect", scopedSpan -> {
                publishNotice
                        .getInfoCollects()
                        .stream()
                        .peek(addInfoRequest -> addInfoRequest.setAids(publishNotice.getAccountList()))
                        .forEach(infoCollect -> {
                            Response<String> response = informationService.addInfoCollect(infoCollect);
                            NoticeInfoKey noticeInfoKey = new NoticeInfoKey();
                            noticeInfoKey.withNid(notice.getId()).withRef(response.checkGet(INFO, "add info fail"));
                            infoMapper.insert(noticeInfoKey);
                        });
            });
            // 注册动态通知
            tracerUtil.newSpan("insert dynamic", scopedSpan -> {
                // 待办
                scopedSpan.annotate("todo");
                publishNotice
                        .getTodoList()
                        .stream()
                        .peek(todo -> todo.setNid(notice.getId()))
                        .forEach(publishTodoMapper::insertSelective);
                // 信息
                scopedSpan.annotate("info");
                publishNotice
                        .getInfoList()
                        .stream()
                        .peek(info -> info.setNid(notice.getId()))
                        .forEach(publishInfoMapper::insertSelective);
                // 组织
                scopedSpan.annotate("organization");
                publishNotice
                        .getOrganizationList()
                        .stream()
                        .peek(info -> info.setNid(notice.getId()))
                        .forEach(publishOrganizationMapper::insertSelective);
            });
            if (notice.getPublishStatus() == STATUS_PUBLISHING) {
                publishNotice(notice.getId());
            }
            return notice.getId();
        });
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean publishNotice(long nid) {
        Notice notice = noticeMapper.selectByPrimaryKey(nid);
        // 已发布
        if (notice.getPublishStatus() == STATUS_PUBLISHED) {
            return false;
        }
        List<String> aids = tracerUtil.newSpan("get account list", scopedSpan -> {
            List<String> ids = getPublishByNid(nid)
                    .stream()
                    .flatMap(publishAccount -> publishAccount.getAccounts().stream())
                    .map(IdName::getId)
                    .collect(Collectors.toList());
            ids.add(notice.getCreator());
            return ids.stream().distinct().collect(Collectors.toList());
        });
        tracerUtil.newSpan("insert account notice", scopedSpan -> {
            aids.stream()
                    .map(accountId -> (AccountNotice) new AccountNotice().withAid(accountId).withNid(notice.getId()))
                    .forEach(accountNoticeMapper::insertSelective);
        });
        if (notice.getPublishStatus() == STATUS_WAIT) {
            tracerUtil.newSpan("update todo", scopedSpan -> {
                todoService.updateAccount(aids, notice.getTodoRef());
            });
            tracerUtil.newSpan("update info", scopedSpan -> {
                NoticeInfoExample example = new NoticeInfoExample();
                example.createCriteria().andNidEqualTo(nid);
                infoMapper.selectByExample(example)
                        .stream()
                        .map(NoticeInfoKey::getRef)
                        .forEach(ref -> informationService.updateAccount(ref, aids));
            });
        }
        tracerUtil.newSpan("update status", scopedSpan -> {
            Notice notice1 = new Notice()
                    .withId(nid)
                    .withPublishStatus(NoticeConst.STATUS_PUBLISHED);
            noticeMapper.updateByPrimaryKeySelective(notice1);
        });
        return true;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<PublishAccount<?>> getPublishByNid(long nid) {
        return Stream.of(
                publicTodoStream(getPublishTodoByNid(nid)),
                publicInfoStream(getPublishInfoByNid(nid)),
                publicOrganizationStream(getPublishOrganizationByNid(nid))
        ).reduce(Stream::concat).get().collect(Collectors.toList());
    }

    @Override
    @NewSpan("get account stream")
    @SuppressWarnings("unchecked")
    public Stream<PublishAccount<?>> publicAccountStream(PublishNotice publishNotice) {
        return (Stream<PublishAccount<?>>) Stream.of(
                Stream.of(new PublishAccount<>().setAccounts(
                        publishNotice.getAccountList()
                                .stream()
                                .map(s -> new IdName<>(s, cacheService.getAccountNameByID(s)))
                                .collect(Collectors.toList())
                )),
                publicTodoStream(publishNotice.getTodoList()),
                publicInfoStream(publishNotice.getInfoList()),
                publicOrganizationStream(publishNotice.getOrganizationList())
        ).reduce(Stream::concat).get();
    }

    @Override
    public Stream<PublishAccount<PublishTodo>> publicTodoStream(List<PublishTodo> todoList) {
        return tracerUtil.newSpan("todo stream",
                (Function<ScopedSpan, Stream<PublishAccount<PublishTodo>>>) span -> todoList.stream().map(this::publishTodo));
    }

    @Override
    public PublishAccount<PublishTodo> publishTodo(PublishTodo todo) {
        return tracerUtil.newSpan("todo: " + todo.getTid(), span -> {
            span.tag("dynamic", todo.getDynamic() + "");
            span.tag("finish", todo.getFinish() + "");
            Response<List<String>> response = todoService.select(todo.getTid(), todo.getFinish());
            List<IdName<String>> accountIds = response.checkGet(TODO, "get todo fail")
                    .stream()
                    .distinct()
                    .map(s -> new IdName<>(s, cacheService.getAccountNameByID(s)))
                    .collect(Collectors.toList());
            return new PublishAccount<>(todo, accountIds);
        });
    }

    @Override
    public Stream<PublishAccount<PublishInfo>> publicInfoStream(List<PublishInfo> infoList) {
        return tracerUtil.newSpan("info stream",
                (Function<ScopedSpan, Stream<PublishAccount<PublishInfo>>>) span -> infoList.stream().map(this::publishInfo));
    }

    @Override
    public PublishAccount<PublishInfo> publishInfo(PublishInfo info) {
        return tracerUtil.newSpan("info: " + info.getIid(), span -> {
            span.tag("dynamic", info.getDynamic() + "");
            Response<List<String>> response = informationService.select(info.getIid(), info.getText());
            List<IdName<String>> accountIds = response.checkGet(INFO, "get info fail")
                    .stream()
                    .distinct()
                    .map(s -> new IdName<>(s, cacheService.getAccountNameByID(s)))
                    .collect(Collectors.toList());
            return new PublishAccount<>(info, accountIds);
        });
    }

    @Override
    public Stream<PublishAccount<PublishOrganization>> publicOrganizationStream(List<PublishOrganization> organizationList) {
        return tracerUtil.newSpan("organization",
                (Function<ScopedSpan, Stream<PublishAccount<PublishOrganization>>>) span -> organizationList.stream().map(this::publishOrganization));
    }

    @Override
    public PublishAccount<PublishOrganization> publishOrganization(PublishOrganization organization) {
        int oid = organization.getOid();
        return tracerUtil.newSpan("organization: " + oid, span -> {
            PublishAccount<PublishOrganization> publishAccount = new PublishAccount<>();
            publishAccount.setSource(organization);
            span.tag("dynamic", organization.getDynamic() + "");
            span.tag("belong", organization.getBelong() + "");
            span.tag("subscribe", organization.getSubscribe() + "");
            ArrayList<String> ids = new ArrayList<>();
            if (organization.getBelong()) {
                Response<List<String>> response = organizationService.getMemberId(oid);
                ids.addAll(response.checkGet(USER_CENTER, "get member fail"));
            }
            if (organization.getSubscribe()) {
                ids.addAll(organizationSubscribeService.getSubscribeAccountId(oid));
            }
            publishAccount.setAccounts(ids
                    .stream()
                    .distinct()
                    .map(s -> new IdName<>(s, cacheService.getAccountNameByID(s)))
                    .collect(Collectors.toList())
            );
            return publishAccount;
        });
    }

    @Override
    @NewSpan("get todo account list")
    @Transactional(rollbackFor = RuntimeException.class)
    public List<PublishTodo> getPublishTodoByNid(@SpanTag("id") long nid) {
        PublishTodoExample example = new PublishTodoExample();
        example.createCriteria().andNidEqualTo(nid);
        return publishTodoMapper.selectByExample(example);
    }

    @Override
    @NewSpan("get info account list")
    @Transactional(rollbackFor = RuntimeException.class)
    public List<PublishInfo> getPublishInfoByNid(@SpanTag("id") long nid) {
        PublishInfoExample example = new PublishInfoExample();
        example.createCriteria().andNidEqualTo(nid);
        return publishInfoMapper.selectByExample(example);
    }

    @Override
    @NewSpan("get organization account list")
    @Transactional(rollbackFor = RuntimeException.class)
    public List<PublishOrganization> getPublishOrganizationByNid(@SpanTag("id") long nid) {
        PublishOrganizationExample example = new PublishOrganizationExample();
        example.createCriteria().andNidEqualTo(nid);
        return publishOrganizationMapper.selectByExample(example);
    }
}
