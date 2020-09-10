package campuslifecenter.notice.service;

import campuslifecenter.notice.model.Notice;
import campuslifecenter.notice.model.projection.InformsInfo;
import campuslifecenter.notice.model.projection.NoticeInfo;

import java.util.List;

public interface NoticeService {

    boolean createNotice(Notice notice);

    NoticeInfo getNotice(Long id);

    List<InformsInfo.InformsNotice> getNoticeByUser(Long id);

    Long readCount(Long id);

    List<NoticeInfo> getNoticeByAuthor(Long id);

}
