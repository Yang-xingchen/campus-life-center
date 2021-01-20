package campuslifecenter.notice.service.impl;

import brave.ScopedSpan;
import brave.Span;
import brave.Tracer;
import campuslifecenter.notice.component.Util;
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
    private Util util;

    @Override
    public String getPublishId(String token) {
        String aid = cacheService.getAccountIdByToken(token);
        String pid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(PUBLISH_PREFIX + pid, aid, 1, TimeUnit.DAYS);
        return pid;
    }

    @Override
    public Long publicNotice(PublishNotice publishNotice) {
        return util.newSpan("public notice", span -> {
            String aid = redisTemplate.opsForValue().get(PUBLISH_PREFIX + publishNotice.getPid());
            span.tag("aid", aid);
            if (!Objects.equals(aid, cacheService.getAccountIdByToken(publishNotice.getToken()))) {
                throw new RuntimeException("auth fail");
            }
            Notice notice = publishNotice.getNotice();
            util.newSpan("init", scopedSpan -> {
                notice.setCreateTime(new Date());
                notice.setFileRef(publishNotice.getPid());
            });
            // 待办信息
            util.newSpan("insert todo", scopedSpan -> {
                Response<String> todoResponse = todoService.add(new AddTodoRequest()
                        .setAids(publishNotice.getAccountList())
                        .setValues(publishNotice.getTodo()));
                if (todoResponse.isSuccess()) {
                    notice.setTodoRef(todoResponse.getData());
                }
            });
            // 通知
            util.newSpan("insert notice", (Consumer<ScopedSpan>) scopedSpan -> noticeMapper.insert(notice));
            // 信息收集
            util.newSpan("insert info collect", scopedSpan -> {
                publishNotice
                        .getInfoCollectList()
                        .stream()
                        .peek(addInfoRequest -> addInfoRequest.setAids(publishNotice.getAccountList()))
                        .map(infoCollect -> {
                            Response<String> response = informationService.addInfoCollect(infoCollect);
                            if (!response.isSuccess()) {
                                throw new RuntimeException("add info fail: " + response.getMessage());
                            }
                            return (NoticeInfo) new NoticeInfo()
                                    .withName(infoCollect.getName())
                                    .withNid(notice.getId())
                                    .withRef(response.getData());
                        })
                        .forEach(infoMapper::insert);
            });
            // 成员
            util.newSpan("insert account notice", scopedSpan -> {
                publishNotice
                        .getAccountList()
                        .stream()
                        .map(accountId -> (AccountNotice) new AccountNotice().withAid(accountId).withNid(notice.getId()))
                        .forEach(accountNoticeMapper::insert);
            });
            // 标签
            util.newSpan("insert tag", scopedSpan -> {
                tagService.addTag(publishNotice.getTag(), notice.getId());
            });
            // 注册动态通知
            util.newSpan("insert dynamic", scopedSpan -> {
                // 待办
                scopedSpan.annotate("todo");
                publishNotice
                        .getTodoList()
                        .forEach(publishTodoMapper::insert);
                // 信息
                scopedSpan.annotate("info");
                publishNotice
                        .getInfoList()
                        .forEach(publishInfoMapper::insert);
                // 组织
                scopedSpan.annotate("organization");
                publishNotice
                        .getOrganizationList()
                        .forEach(publishOrganizationMapper::insert);
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
        return util.newSpan("todo stream",
                (Function<ScopedSpan, Stream<PublishAccount<PublishTodo>>>) span -> todoList.stream().map(this::publishTodo));
    }

    @Override
    public PublishAccount<PublishTodo> publishTodo(PublishTodo todo) {
        return util.newSpan("todo: " + todo.getTid(), span -> {
            span.tag("dynamic", todo.getDynamic() + "");
            span.tag("finish", todo.getFinish() + "");
            Response<List<String>> response = todoService.select(todo.getTid(), todo.getFinish());
            if (!response.isSuccess()) {
                span.finish();
                throw new RuntimeException("get todo fail: " + response.getMessage());
            }
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
        return util.newSpan("info stream",
                (Function<ScopedSpan, Stream<PublishAccount<PublishInfo>>>) span -> infoList.stream().map(this::publishInfo));
    }

    @Override
    public PublishAccount<PublishInfo> publishInfo(PublishInfo info) {
        return util.newSpan("info: " + info.getIid(), span -> {
            span.tag("dynamic", info.getDynamic() + "");
            Response<List<String>> response = informationService.select(info.getIid(), info.getText());
            if (!response.isSuccess()) {
                span.finish();
                throw new RuntimeException("get info fail: " + response.getMessage());
            }
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
        return util.newSpan("organization",
                (Function<ScopedSpan, Stream<PublishAccount<PublishOrganization>>>) span -> organizationList.stream().map(this::publishOrganization));
    }

    @Override
    public PublishAccount<PublishOrganization> publishOrganization(PublishOrganization organization) {
        int oid = organization.getOid();
        return util.newSpan("organization: " + oid, span -> {
            PublishAccount<PublishOrganization> publishAccount = new PublishAccount<>();
            publishAccount.setSource(organization);
            span.tag("dynamic", organization.getDynamic() + "");
            span.tag("belong", organization.getBelong() + "");
            span.tag("subscribe", organization.getSubscribe() + "");
            ArrayList<String> ids = new ArrayList<>();
            if (organization.getBelong()) {
                Response<List<String>> memberIds = organizationService.getMemberId(oid);
                if (memberIds.isSuccess()) {
                    ids.addAll(memberIds.getData());
                }
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
