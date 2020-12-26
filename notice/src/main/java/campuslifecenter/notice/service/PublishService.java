package campuslifecenter.notice.service;

import campuslifecenter.notice.model.PublishNotice;

import java.util.stream.Stream;

public interface PublishService {

    Stream<String> publicAccountStream(PublishNotice publishNotice);

    Stream<String> publicTodoStream(PublishNotice publishNotice);

    Stream<String> publicInfoStream(PublishNotice publishNotice);

    Stream<String> publicOrganizationStream(PublishNotice publishNotice);
}
