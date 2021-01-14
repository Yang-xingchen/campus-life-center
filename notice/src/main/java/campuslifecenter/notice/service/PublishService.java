package campuslifecenter.notice.service;

import campuslifecenter.notice.entry.PublishInfo;
import campuslifecenter.notice.entry.PublishOrganization;
import campuslifecenter.notice.entry.PublishTodo;
import campuslifecenter.notice.model.PublishAccount;
import campuslifecenter.notice.model.PublishNotice;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface PublishService {

    String getPublishId(String token);

    Long publicNotice(PublishNotice publishNotice);

    Stream<PublishAccount<?>> publicAccountStream(PublishNotice publishNotice);

    Stream<PublishAccount<PublishTodo>> publicTodoStream(List<PublishTodo> todoList);

    Stream<PublishAccount<PublishInfo>> publicInfoStream(List<PublishInfo> infoList);

    Stream<PublishAccount<PublishOrganization>> publicOrganizationStream(List<PublishOrganization> organizationList);

    List<PublishTodo> getPublishTodoByNid(long nid);

    List<PublishInfo> getPublishInfoByNid(long nid);

    List<PublishOrganization> getPublishOrganizationByNid(long nid);
}
