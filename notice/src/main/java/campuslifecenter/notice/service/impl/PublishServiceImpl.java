package campuslifecenter.notice.service.impl;

import brave.ScopedSpan;
import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.ProcessException;
import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.common.model.Response;
import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.mapper.*;
import campuslifecenter.notice.model.*;
import campuslifecenter.notice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Service
@Transactional(rollbackFor = RuntimeException.class)
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
    private RedisTemplate<String, String> redisTemplate;
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
    @Value("${notice.publish-notice}")
    private String PUBLISH_PREFIX;

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
    public Long publicNotice(PublishNotice publishNotice) {
        return tracerUtil.newSpan("public notice", span -> {
            String aid = redisTemplate.opsForValue().get(PUBLISH_PREFIX + publishNotice.getPid());
            span.tag("aid", aid);
            if (!Objects.equals(aid, cacheService.getAccountIdByToken(publishNotice.getToken()))) {
                throw new ResponseException("auth fail", 403);
            }
            Notice notice = publishNotice.getNotice();
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
                ProcessException.check(TODO, "insert todo fail", response);
                notice.setTodoRef(response.getData());
            });
            // 通知
            tracerUtil.newSpan("insert notice", (Consumer<ScopedSpan>) scopedSpan -> noticeMapper.insertSelective(notice));
            // 信息收集
            tracerUtil.newSpan("insert info collect", scopedSpan -> {
                publishNotice
                        .getInfoCollects()
                        .stream()
                        .peek(addInfoRequest -> addInfoRequest.setAids(publishNotice.getAccountList()))
                        .map(infoCollect -> {
                            Response<String> response = informationService.addInfoCollect(infoCollect);
                            ProcessException.check(INFO, "add info fail", response);
                            String[] data = response.getData().split(":");
                            return (NoticeInfo) new NoticeInfo()
                                    .withRootId(Long.parseLong(data[1]))
                                    .withNid(notice.getId())
                                    .withRef(data[0]);
                        })
                        .forEach(infoMapper::insertSelective);
            });
            // 成员
            tracerUtil.newSpan("insert account notice", scopedSpan -> {
                publishNotice
                        .getAccountList()
                        .stream()
                        .map(accountId -> (AccountNotice) new AccountNotice().withAid(accountId).withNid(notice.getId()))
                        .forEach(accountNoticeMapper::insertSelective);
            });
            // 标签
            tracerUtil.newSpan("insert tag", scopedSpan -> {
                tagService.addTag(publishNotice.getTag(), notice.getId());
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
            return notice.getId();
        });
    }

    @Override
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
            ProcessException.check(TODO, "get todo fail", response);
            List<IdName<String>> accountIds = response.getData()
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
            ProcessException.check(INFO, "get info fail", response);
            List<IdName<String>> accountIds = response.getData()
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
                ProcessException.check(USER_CENTER, "get member fail", response);
                ids.addAll(response.getData());
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
    public List<PublishTodo> getPublishTodoByNid(long nid) {
        PublishTodoExample example = new PublishTodoExample();
        example.createCriteria().andNidEqualTo(nid);
        return publishTodoMapper.selectByExample(example);
    }

    @Override
    public List<PublishInfo> getPublishInfoByNid(long nid) {
        PublishInfoExample example = new PublishInfoExample();
        example.createCriteria().andNidEqualTo(nid);
        return publishInfoMapper.selectByExample(example);
    }

    @Override
    public List<PublishOrganization> getPublishOrganizationByNid(long nid) {
        PublishOrganizationExample example = new PublishOrganizationExample();
        example.createCriteria().andNidEqualTo(nid);
        return publishOrganizationMapper.selectByExample(example);
    }
}
