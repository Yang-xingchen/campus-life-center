package campuslifecenter.notice.service;

import campuslifecenter.notice.entry.Notice;
import campuslifecenter.notice.entry.PublishInfo;
import campuslifecenter.notice.entry.PublishOrganization;
import campuslifecenter.notice.entry.PublishTodo;
import campuslifecenter.notice.model.PublishAccount;
import campuslifecenter.notice.model.PublishNotice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface PublishService {

    String getPublishId(String token);

    String getPublishAid(String pid);

    Long publicNotice(PublishNotice publishNotice);

    boolean publishNotice(long nid);

    Stream<PublishAccount<?>> publicAccountStream(PublishNotice publishNotice);

    Stream<PublishAccount<PublishTodo>> publicTodoStream(List<PublishTodo> todoList);

    PublishAccount<PublishTodo> publishTodo(PublishTodo todo);

    Stream<PublishAccount<PublishInfo>> publicInfoStream(List<PublishInfo> infoList);

    PublishAccount<PublishInfo> publishInfo(PublishInfo publishInfo);

    Stream<PublishAccount<PublishOrganization>> publicOrganizationStream(List<PublishOrganization> organizationList);

    PublishAccount<PublishOrganization> publishOrganization(PublishOrganization organization);

    boolean publishNotice(Notice notice, List<String> aids);

    List<PublishAccount<?>> getPublishByNid(long nid);

    List<PublishTodo> getPublishTodoByNid(long nid);

    List<PublishInfo> getPublishInfoByNid(long nid);

    List<PublishOrganization> getPublishOrganizationByNid(long nid);
}
