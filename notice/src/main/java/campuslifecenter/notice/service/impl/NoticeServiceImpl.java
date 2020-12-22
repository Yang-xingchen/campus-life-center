package campuslifecenter.notice.service.impl;

import campuslifecenter.notice.component.NoticeStream;
import campuslifecenter.notice.entry.AccountNoticeExample;
import campuslifecenter.notice.entry.Notice;
import campuslifecenter.notice.entry.NoticeMateExample;
import campuslifecenter.notice.entry.NoticeTagExample;
import campuslifecenter.notice.mapper.*;
import campuslifecenter.notice.model.AccountInfo;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.model.PublicNotice;
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
    private NoticeMateMapper noticeMateMapper;

    @Autowired
    private NoticeStream noticeStream;

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
                    info.setTag(noticeTagMapper.selectByExample(example));
                })
                .peek(info -> {
                    NoticeMateExample example = new NoticeMateExample();
                    example.createCriteria().andNidEqualTo(info.getNotice().getId());
                    info.setMate(noticeMateMapper.selectByExample(example));
                })
                .collect(Collectors.toList());
    }

    @Override
    public AccountNoticeInfo getNoticeById(int id) {
        return AccountNoticeInfo.createByNotice(noticeMapper.selectByPrimaryKey(id));
    }

    @Override
    public Long publicNotice(PublicNotice publicNotice) {
        Notice notice = publicNotice.getNotice();
        noticeMapper.insert(notice);
        if (!publicNotice.isDynamic()) {
            // TODO 静态

        } else {
            // TODO 动态
        }
        return notice.getId();
    }

}
