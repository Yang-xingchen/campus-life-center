package campuslifecenter.notice.service;

import campuslifecenter.notice.model.AccountInfo;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.model.PublishNotice;

import java.util.List;

public interface NoticeService {

    List<AccountNoticeInfo> getAllNoticeByAid(AccountInfo accountInfo);

    AccountNoticeInfo getNoticeById(long id);

    Long publicNotice(PublishNotice publishNotice);

}
