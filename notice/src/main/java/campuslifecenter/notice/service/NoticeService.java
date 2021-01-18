package campuslifecenter.notice.service;

import campuslifecenter.notice.entry.AccountNotice;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.model.PublishNotice;

import java.util.List;

public interface NoticeService {

    List<AccountNoticeInfo> getAllNoticeOperationByAid(String aid);

    AccountNoticeInfo getNoticeById(long nid);

    AccountNoticeInfo setNoticeAccountOperation(AccountNoticeInfo noticeInfo, String aid);

    boolean updateAccountOperation(AccountNotice accountNotice);

    List<AccountNotice> getAllAccountOperationByNid(long nid);

    Long getNoticeIdByTodoRef(String ref);

    List<String> getTodoRefByCreator(String aid);
}
