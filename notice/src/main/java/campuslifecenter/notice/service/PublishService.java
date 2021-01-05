package campuslifecenter.notice.service;

import campuslifecenter.notice.entry.PublishInfo;
import campuslifecenter.notice.entry.PublishOrganization;
import campuslifecenter.notice.entry.PublishTodo;
import campuslifecenter.notice.model.PublishNotice;

import java.util.List;
import java.util.stream.Stream;

public interface PublishService {

    Stream<String> publicAccountStream(PublishNotice publishNotice);

    Stream<String> publicTodoStream(List<PublishTodo> todoList, long nid);

    Stream<String> publicInfoStream(List<PublishInfo> infoList);

    Stream<String> publicOrganizationStream(List<PublishOrganization> organizationList);

}
