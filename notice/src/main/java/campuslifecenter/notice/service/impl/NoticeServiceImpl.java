package campuslifecenter.notice.service.impl;

import campuslifecenter.notice.component.NoticeStream;
import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.mapper.*;
import campuslifecenter.notice.model.AccountInfo;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.model.PublishNotice;
import campuslifecenter.notice.model.Response;
import campuslifecenter.notice.service.InformationService;
import campuslifecenter.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private DynamicTodoObserveMapper todoObserveMapper;
    @Autowired
    private DynamicInfoObserveMapper infoObserveMapper;
    @Autowired
    private DynamicOrganizationObserveMapper organizationObserveMapper;

    @Autowired
    private NoticeStream noticeStream;
    @Autowired
    private InformationService informationService;

    @Override
    public List<AccountNoticeInfo> getAllNoticeByAid(AccountInfo accountInfo){
        AccountNoticeExample noticeExample = new AccountNoticeExample();
        noticeExample.createCriteria().andAidEqualTo(accountInfo.getSignId());
        return accountNoticeMapper
                .selectByExample(noticeExample)
                .stream()
                .map(AccountNoticeInfo::createByAccountNotice)
                .peek(info -> info.setNotice(noticeMapper.selectByPrimaryKey(info.getAccountOperation().getNid())))
                .peek(info -> {
                    NoticeTagExample example = new NoticeTagExample();
                    example.createCriteria().andNidEqualTo(info.getNotice().getId());
                    info.withNoticeTag(noticeTagMapper.selectByExample(example));
                })
                .peek(info -> {
                    NoticeTodoExample example = new NoticeTodoExample();
                    example.createCriteria().andNidEqualTo(info.getNotice().getId());
                    info.setTodoList(noticeTodoMapper.selectByExample(example));
                })
                .collect(Collectors.toList());
    }

    @Override
    public AccountNoticeInfo getNoticeById(long id) {
        NoticeTagExample tagExample = new NoticeTagExample();
        tagExample.createCriteria().andNidEqualTo(id);
        NoticeTodoExample mateExample = new NoticeTodoExample();
        mateExample.createCriteria().andNidEqualTo(id);
        return AccountNoticeInfo.createByNotice(noticeMapper.selectByPrimaryKey(id))
                .withNoticeTag(noticeTagMapper.selectByExample(tagExample))
                .setTodoList(noticeTodoMapper.selectByExample(mateExample));
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
                    todo.setValue(response.getData());
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
