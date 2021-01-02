package campuslifecenter.notice.service;

import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.model.PublishNotice;

import java.util.List;

public interface NoticeService {

    List<AccountNoticeInfo> getAllNoticeOperationByAid(String aid);

    AccountNoticeInfo getNoticeById(long nid);

    AccountNoticeInfo setNoticeAccountOperation(AccountNoticeInfo noticeInfo, String aid);

    Long publicNotice(PublishNotice publishNotice);
}
