package campuslifecenter.notice.service;

import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.model.PublishNotice;

import java.util.List;

public interface PublishService {

    String getPublishId(String token);

    String getPublishAid(String pid);

    Long publishNotice(PublishNotice publishNotice);

    boolean publishNoticeAccount(Notice notice, List<String> aids);

    boolean acceptPublishWaitNotice(long nid, String aid);

    boolean rejectPublishWaitNotice(long nid, String aid);

    List<Long> getWaitPublishIds(String aid);
}
