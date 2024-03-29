package campuslifecenter.notice.service;

import campuslifecenter.notice.entry.AccountNotice;
import campuslifecenter.notice.entry.Notice;
import campuslifecenter.notice.entry.NoticeUpdateLog;
import campuslifecenter.notice.model.AccountNoticeInfo;
import campuslifecenter.notice.model.NoticeInfo;
import campuslifecenter.notice.model.PublishNotice;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;

import java.util.List;

public interface NoticeService {

    List<AccountNoticeInfo> getAllNoticeOperationByAid(String aid);

    AccountNoticeInfo getNoticeById(long nid);

    AccountNotice getNoticeAccountOperation(long nid, String aid);

    boolean updateAccountOperation(AccountNotice accountNotice);

    List<AccountNotice> getAllAccountOperationByNid(long nid);

    Long getNoticeIdByRef(String ref);

    List<String> getRefByCreator(String aid);

    void update(NoticeInfo notice, NoticeInfo oldNotice);

    List<NoticeUpdateLog> updateLog(long id);
}
