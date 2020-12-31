package campuslifecenter.notice.service.impl;

import campuslifecenter.notice.component.NoticeStream;
import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.mapper.*;
import campuslifecenter.notice.model.*;
import campuslifecenter.notice.service.AccountService;
import campuslifecenter.notice.service.InformationService;
import campuslifecenter.notice.service.NoticeService;
import campuslifecenter.notice.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private AccountNoticeMapper accountNoticeMapper;
    @Autowired
    private NoticeTagMapper noticeTagMapper;
    @Autowired
    private NoticeTodoMapper noticeTodoMapper;
    @Autowired
    private AccountNoticeTodoMapper accountNoticeTodoMapper;
    @Autowired
    private DynamicTodoObserveMapper todoObserveMapper;
    @Autowired
    private DynamicInfoObserveMapper infoObserveMapper;
    @Autowired
    private DynamicOrganizationObserveMapper organizationObserveMapper;

    @Autowired
    private NoticeStream noticeStream;
    @Autowired
    private AccountService accountService;
    @Autowired
    private InformationService informationService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static final String ACCOUNT_NAME_PREFIX = "accountNameCache:";
    public static final String ORGANIZATION_NAME_PREFIX = "organizationNameCache:";

    @Override
    public List<AccountNoticeInfo> getAllNoticeByAid(AccountInfo accountInfo){
        AccountNoticeExample noticeExample = new AccountNoticeExample();
        noticeExample.createCriteria().andAidEqualTo(accountInfo.getSignId());
        return accountNoticeMapper
                .selectByExample(noticeExample)
                .stream()
                .map(AccountNoticeInfo::createByAccountNotice)
                .peek(info -> info.setNotice(noticeMapper.selectByPrimaryKey(info.getId())))
                .peek(info -> {
                    NoticeTagExample example = new NoticeTagExample();
                    example.createCriteria().andNidEqualTo(info.getId());
                    info.withNoticeTag(noticeTagMapper.selectByExample(example));
                })
                .peek(info -> {
                    NoticeTodoExample example = new NoticeTodoExample();
                    example.createCriteria().andNidEqualTo(info.getId());
                    info.setTodoList(noticeTodoMapper
                            .selectByExample(example)
                            .stream()
                            .map(noticeTodo -> {
                                AccountNoticeTodoKey todoKey = new AccountNoticeTodoKey();
                                todoKey.withNid(noticeTodo.getNid())
                                        .withId(noticeTodo.getId())
                                        .withAid(accountInfo.getSignId());
                                AccountNoticeTodo accountNoticeTodo = accountNoticeTodoMapper.selectByPrimaryKey(todoKey);
                                return new AccountNoticeInfo.AccountTodo()
                                        .setNoticeTodo(noticeTodo)
                                        .setAccountNoticeTodo(accountNoticeTodo);
                            })
                            .collect(Collectors.toList())
                    );
                })
                .peek(this::setCreatorName)
                .peek(this::setOrganizationName)
                .collect(Collectors.toList());
    }

    @Override
    public AccountNoticeInfo getNoticeById(long id) {
        NoticeTagExample tagExample = new NoticeTagExample();
        tagExample.createCriteria().andNidEqualTo(id);
        NoticeTodoExample mateExample = new NoticeTodoExample();
        mateExample.createCriteria().andNidEqualTo(id);
        AccountNoticeInfo accountNoticeInfo = AccountNoticeInfo.createByNotice(noticeMapper.selectByPrimaryKey(id))
                .withNoticeTag(noticeTagMapper.selectByExample(tagExample))
                .setTodoList(noticeTodoMapper
                        .selectByExample(mateExample)
                        .stream()
                        .map(noticeTodo -> new AccountNoticeInfo.AccountTodo().setNoticeTodo(noticeTodo))
                        .collect(Collectors.toList()));
        setCreatorName(accountNoticeInfo);
        setOrganizationName(accountNoticeInfo);
        return accountNoticeInfo;
    }

    private AccountNoticeInfo setOrganizationName(AccountNoticeInfo info) {
        return info.setOrganizationName(Optional
                .ofNullable(redisTemplate
                        .opsForValue()
                        .get(ORGANIZATION_NAME_PREFIX + info.getOrganization()))
                .orElseGet(() -> {
                    Response<Organization> response = organizationService
                            .getOrganization(info.getOrganization());
                    if (!response.isSuccess()) {
                        throw new RuntimeException("organization not found:" + response.getMessage());
                    }
                    return response.getData().getName();
                })
        );
    }

    private AccountNoticeInfo setCreatorName(AccountNoticeInfo info) {
        return info.setCreatorName(Optional
                .ofNullable(redisTemplate.opsForValue().get(ACCOUNT_NAME_PREFIX + info.getCreator()))
                .orElseGet(() -> {
                    Response<AccountInfo> response = accountService.infoById(info.getCreator());
                    if (!response.isSuccess()) {
                        throw new RuntimeException("account not found:" + response.getMessage());
                    }
                    return response.getData().getName();
                })
        );
    }

    @Override
    public Long publicNotice(PublishNotice publishNotice) {
        Notice notice = publishNotice.getNotice();
        noticeMapper.insert(notice);
        publishNotice
                .getAccountList()
                .stream()
                .map(accountId -> (AccountNotice) new AccountNotice().withAid(accountId).withNid(notice.getId()))
                .forEach(accountNoticeMapper::insert);
        // 待办信息
        publishNotice
                .getNoticeTodoList()
                .stream()
                .filter(todo -> todo.getType() == 0)
                .peek(todo -> todo.setNid(notice.getId()))
                .forEach(noticeTodoMapper::insert);
        publishNotice
                .getInfoCollectList()
                .stream()
                .map(collect-> {
                    NoticeTodo todo = new NoticeTodo();
                    todo.setNid(notice.getId());
                    todo.setType(1);
                    Response<String> response = informationService.addInfoCollect(collect);
                    if (!response.isSuccess()) {
                        throw new RuntimeException("add info collect fail: " + response.getMessage());
                    }
                    todo.setTypeValue(response.getData());
                    return todo;
                })
                .forEach(noticeTodoMapper::insert);
        // 注册动态通知
        // 待办
        publishNotice
                .getTodoList()
                .stream()
                .filter(PublishNotice.PublishTodo::isDynamic)
                .map(todo -> todo.toEntry(notice.getId()))
                .forEach(todoObserveMapper::insert);
        // 信息
        publishNotice
                .getInfoList()
                .stream()
                .filter(PublishNotice.PublishInfo::isDynamic)
                .map(info -> info.toEntry(notice.getId()))
                .forEach(infoObserveMapper::insert);
        // 组织
        publishNotice
                .getOrganizationList()
                .stream()
                .filter(PublishNotice.PublishOrganization::isDynamic)
                .map(organization -> organization.toEntry(notice.getId()))
                .forEach(organizationObserveMapper::insert);
        return notice.getId();
    }

}
