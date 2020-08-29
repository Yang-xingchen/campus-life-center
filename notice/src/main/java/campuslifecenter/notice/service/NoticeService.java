package campuslifecenter.notice.service;

import campuslifecenter.notice.model.Notice;

import java.util.List;

public interface NoticeService {

    boolean createNotice(Notice notice);

    Notice getNotice(Long id);

    List<Notice> getNoticeByUser(Long id);

    Long readCount(Long id);

    List<Notice> getNoticeByAuthor(Long id);

}
