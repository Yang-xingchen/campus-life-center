package campuslifecenter.notice.service.impl;

import campuslifecenter.notice.entry.NoticeTodoKey;
import campuslifecenter.notice.model.PublishNotice;
import campuslifecenter.notice.service.NoticeTodoService;
import campuslifecenter.notice.service.OrganizationService;
import campuslifecenter.notice.service.OrganizationSubscribeService;
import campuslifecenter.notice.service.PublishAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PublishAccountServiceImpl implements PublishAccountService {

    @Autowired
    private NoticeTodoService noticeTodoService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrganizationSubscribeService organizationSubscribeService;

    @Override
    public Stream<String> publicAccountStream(PublishNotice publishNotice) {
        return Stream.of(
                Optional.ofNullable(publishNotice.getAccountList()).orElseGet(ArrayList::new).stream(),
                publicTodoStream(publishNotice),
                publicInfoStream(publishNotice),
                publicOrganizationStream(publishNotice)
        ).reduce(Stream::concat).get().distinct();
    }

    @Override
    public Stream<String> publicTodoStream(PublishNotice publishNotice) {
        return publishNotice
                .getTodoList()
                .stream()
                .map(todo -> noticeTodoService.getTodoAccountIdByNoticeId(
                        new NoticeTodoKey()
                                .withId(todo.getId())
                                .withNid(publishNotice.getNotice().getId()),
                        todo.isFinish())
                )
                .flatMap(List::stream)
                .distinct();
    }

    @Override
    public Stream<String> publicInfoStream(PublishNotice publishNotice) {
        // TODO
        return Stream.of();
    }

    @Override
    public Stream<String> publicOrganizationStream(PublishNotice publishNotice) {
        return Stream.concat(
                // 加入组织的成员
                publishNotice.getOrganizationList()
                        .stream()
                        .filter(PublishNotice.PublishOrganization::isBelong)
                        .map(organization1 -> organizationService.getMemberId(organization1.getId()))
                        .peek(response -> {
                            if (!response.isSuccess()) {
                                throw new RuntimeException("get organization member fail:" + response.getMessage());
                            }
                        })
                        .flatMap(listResponse -> listResponse.getData().stream()),
                // 订阅组织的成员
                publishNotice.getOrganizationList()
                        .stream()
                        .filter(PublishNotice.PublishOrganization::isSubscribe)
                        .map(organization -> organizationSubscribeService.getSubscribeAccountId(organization.getId()))
                        .flatMap(List::stream)
        ).distinct();
    }
}
