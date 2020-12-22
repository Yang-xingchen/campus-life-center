package campuslifecenter.notice.service;

import campuslifecenter.notice.model.AccountInfo;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.model.PublicNotice;

import java.util.List;

public interface NoticeService {

    List<AccountNoticeInfo> getAllNoticeByAid(AccountInfo accountInfo);

    AccountNoticeInfo getNoticeById(int id);

    Long publicNotice(PublicNotice accountNoticeInfo);

}
