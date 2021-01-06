package campuslifecenter.notice.service.impl;

import campuslifecenter.notice.component.NoticeStream;
import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.mapper.*;
import campuslifecenter.notice.model.*;
import campuslifecenter.notice.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
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
    private PublishTodoMapper publishTodoMapper;
    @Autowired
    private PublishInfoMapper publishInfoMapper;
    @Autowired
    private PublishOrganizationMapper publishOrganizationMapper;

    @Autowired
    private NoticeStream noticeStream;
    @Autowired
    private InformationService informationService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public ObjectMapper objectMapper = new ObjectMapper();

    @Value("${notice.cache.notice}")
    public String NOTICE_PREFIX;

    @Override
    public List<AccountNoticeInfo> getAllNoticeOperationByAid(String aid) {
        AccountNoticeExample noticeExample = new AccountNoticeExample();
        noticeExample.createCriteria().andAidEqualTo(aid);
        return accountNoticeMapper
                .selectByExample(noticeExample)
                .stream()
                .map(AccountNoticeInfo::createByAccountNotice)
                .collect(Collectors.toList());
    }

    @Override
    public AccountNoticeInfo getNoticeById(long nid) {
        BoundValueOperations<String, String> noticeOps = redisTemplate.boundValueOps(NOTICE_PREFIX + nid);
        if (noticeOps.get() != null) {
            try {
                return objectMapper.readValue(noticeOps.get(), AccountNoticeInfo.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        NoticeTagExample tagExample = new NoticeTagExample();
        tagExample.createCriteria().andNidEqualTo(nid);
        NoticeTodoExample todoExample = new NoticeTodoExample();
        todoExample.createCriteria().andNidEqualTo(nid);
        AccountNoticeInfo accountNoticeInfo = AccountNoticeInfo.createByNotice(noticeMapper.selectByPrimaryKey(nid))
                .withNoticeTag(noticeTagMapper.selectByExample(tagExample))
                .setTodoList(noticeTodoMapper
                        .selectByExample(todoExample)
                        .stream()
                        .map(noticeTodo -> new AccountTodo().setNoticeTodo(noticeTodo))
                        .collect(Collectors.toList()));
        setCreatorName(accountNoticeInfo);
        setOrganizationName(accountNoticeInfo);
        try {
            noticeOps.set(objectMapper.writeValueAsString(accountNoticeInfo), 1, TimeUnit.DAYS);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return accountNoticeInfo;
    }

    @Override
    public AccountNoticeInfo setNoticeAccountOperation(AccountNoticeInfo noticeInfo, String aid) {
        return noticeInfo.setAccountOperation(accountNoticeMapper.selectByPrimaryKey(
                new AccountNoticeKey().withAid(aid).withNid(noticeInfo.getId())
        ));
    }

    @Override
    public boolean updateAccountOperation(AccountNotice accountNotice) {
        return accountNoticeMapper.updateByPrimaryKey(accountNotice) == 1;
    }

    private AccountNoticeInfo setOrganizationName(AccountNoticeInfo info) {
        return info.setOrganizationName(cacheService.getOrganizationName(info.getOrganization()));
    }

    private AccountNoticeInfo setCreatorName(AccountNoticeInfo info) {
        return info.setCreatorName(cacheService.getAccountNameByID(info.getCreator()));
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
    public List<AccountNotice> getAllAccountOperationByNid(long nid) {
        AccountNoticeExample example = new AccountNoticeExample();
        example.createCriteria().andNidEqualTo(nid);
        return accountNoticeMapper.selectByExample(example);
    }

}
