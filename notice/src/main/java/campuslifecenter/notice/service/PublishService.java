package campuslifecenter.notice.service;

import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.model.PublishNotice;

import java.util.List;

public interface PublishService {

    String getPublishId(String token);

    String getPublishAid(String pid);

    Long publishNotice(PublishNotice publishNotice);

    boolean publishNoticeAccount(Notice notice, List<String> aids);
}
