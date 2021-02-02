package campuslifecenter.notice.service;

import campuslifecenter.notice.entry.AccountNotice;
import campuslifecenter.notice.entry.Notice;
import campuslifecenter.notice.entry.NoticeUpdateLog;
import campuslifecenter.notice.model.AccountNoticeInfo;
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

    Long getNoticeIdByTodoRef(String ref);

    @NewSpan("get notice")
    Long getNoticeIdByFileRef(@SpanTag("file ref") String ref);

    List<String> getTodoRefByCreator(String aid);

    void update(Notice notice, Notice oldNotice);

    List<NoticeUpdateLog> updateLog(long id);
}
