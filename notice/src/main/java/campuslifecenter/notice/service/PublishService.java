package campuslifecenter.notice.service;

import campuslifecenter.notice.model.PublishNotice;

import java.util.List;
import java.util.stream.Stream;

public interface PublishService {

    Stream<String> publicAccountStream(PublishNotice publishNotice);

    Stream<String> publicTodoStream(List<PublishNotice.PublishTodo> todoList, long nid);

    Stream<String> publicInfoStream(List<PublishNotice.PublishInfo> infoList);

    Stream<String> publicOrganizationStream(List<PublishNotice.PublishOrganization> organizationList);

}
