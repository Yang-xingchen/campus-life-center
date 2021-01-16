package campuslifecenter.notice.service.impl;

import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.mapper.*;
import campuslifecenter.notice.model.IdName;
import campuslifecenter.notice.model.PublishAccount;
import campuslifecenter.notice.model.PublishNotice;
import campuslifecenter.notice.model.Response;
import campuslifecenter.notice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
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

    @Override
    public String getPublishId(String token) {
        String aid = cacheService.getAccountIdByToken(token);
        String pid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(PUBLISH_PREFIX + pid, aid, 1, TimeUnit.DAYS);
        return pid;
    }

    @Override
    public Long publicNotice(PublishNotice publishNotice) {
        String aid = redisTemplate.opsForValue().get(PUBLISH_PREFIX + publishNotice.getPid());
        if (!Objects.equals(aid, cacheService.getAccountIdByToken(publishNotice.getToken()))) {
            throw new RuntimeException("auth fail");
        }
        Notice notice = publishNotice.getNotice();
        notice.setCreateTime(new Date());
        notice.setFileRef(publishNotice.getPid());
        publishNotice
                .getAccountList()
                .stream()
                .map(accountId -> (AccountNotice) new AccountNotice().withAid(accountId).withNid(notice.getId()))
                .forEach(accountNoticeMapper::insert);
        tagService.addTag(publishNotice.getTag(), notice.getId());
        // 待办信息
        publishNotice.getTodo().setAids(publishNotice.getAccountList());
        Response<String> todoResponse = todoService.add(publishNotice.getTodo());
        if (todoResponse.isSuccess()) {
            notice.setTodoRef(todoResponse.getData());
        }
        publishNotice
                .getInfoCollectList()
                .stream()
                .peek(addInfoRequest -> addInfoRequest.setAids(publishNotice.getAccountList()))
                .map(infoCollect -> {
                    final Response<String> response = informationService.addInfoCollect(infoCollect);
                    if (!response.isSuccess()) {
                        throw new RuntimeException("add info fail: " + response.getMessage());
                    }
                    return (NoticeInfo) new NoticeInfo()
                            .withName(infoCollect.getName())
                            .withNid(notice.getId())
                            .withRef(response.getData());
                })
                .forEach(infoMapper::insert);
        noticeMapper.insert(notice);
        // 注册动态通知
        // 待办
        publishNotice
                .getTodoList()
                .forEach(publishTodoMapper::insert);
        // 信息
        publishNotice
                .getInfoList()
                .forEach(publishInfoMapper::insert);
        // 组织
        publishNotice
                .getOrganizationList()
                .forEach(publishOrganizationMapper::insert);
        return notice.getId();
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
        ).reduce(Stream::concat).get().distinct();
    }

    @Override
    public Stream<PublishAccount<PublishTodo>> publicTodoStream(List<PublishTodo> todoList) {
        return todoList
                .stream()
                .map(todo -> {
                    Response<List<String>> response = todoService.select(todo.getTid(), todo.getFinish());
                    if (!response.isSuccess()) {
                        throw new RuntimeException("get todo fail: " + response.getMessage());
                    }
                    List<IdName<String>> accountIds = response.getData()
                            .stream()
                            .map(s -> new IdName<>(s, cacheService.getAccountNameByID(s)))
                            .collect(Collectors.toList());
                    return new PublishAccount<>(todo, accountIds);
                })
                .distinct();
    }

    @Override
    public Stream<PublishAccount<PublishInfo>> publicInfoStream(List<PublishInfo> infoList) {
        return infoList
                .stream()
                .map(publishInfo -> {
                    Response<List<String>> response = informationService.select(publishInfo.getIid(), publishInfo.getText());
                    if (!response.isSuccess()) {
                        throw new RuntimeException("get info fail: " + response.getMessage());
                    }
                    List<IdName<String>> accountIds = response.getData()
                            .stream()
                            .map(s -> new IdName<>(s, cacheService.getAccountNameByID(s)))
                            .collect(Collectors.toList());
                    return new PublishAccount<>(publishInfo, accountIds);
                })
                .distinct();
    }

    @Override
    public Stream<PublishAccount<PublishOrganization>> publicOrganizationStream(List<PublishOrganization> organizationList) {
        return organizationList
                .stream()
                .map(organization -> {
                    PublishAccount<PublishOrganization> publishAccount = new PublishAccount<>();
                    publishAccount.setSource(organization);
                    int oid = organization.getOid();
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
                            .map(s->new IdName<>(s, cacheService.getAccountNameByID(s)))
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
