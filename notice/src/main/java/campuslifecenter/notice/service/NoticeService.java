package campuslifecenter.notice.service;

import campuslifecenter.notice.model.AccountInfo;
import campuslifecenter.notice.model.NoticeInfo;

import java.util.List;

public interface NoticeService {

    List<NoticeInfo> getNoticeByAid(AccountInfo accountInfo);

}
