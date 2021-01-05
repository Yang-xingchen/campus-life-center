package campuslifecenter.notice.service.impl;

import campuslifecenter.notice.entry.NoticeTodoKey;
import campuslifecenter.notice.entry.PublishInfo;
import campuslifecenter.notice.entry.PublishOrganization;
import campuslifecenter.notice.entry.PublishTodo;
import campuslifecenter.notice.model.PublishNotice;
import campuslifecenter.notice.model.Response;
import campuslifecenter.notice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PublishServiceImpl implements PublishService {

    @Autowired
    private NoticeTodoService noticeTodoService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrganizationSubscribeService organizationSubscribeService;
    @Autowired
    private InformationService informationService;

    @Override
    public Stream<String> publicAccountStream(PublishNotice publishNotice) {
        return Stream.of(
                Optional.ofNullable(publishNotice.getAccountList()).orElseGet(ArrayList::new).stream(),
                publicTodoStream(publishNotice.getTodoList(), publishNotice.getNotice().getId()),
                publicInfoStream(publishNotice.getInfoList()),
                publicOrganizationStream(publishNotice.getOrganizationList())
        ).reduce(Stream::concat).get().distinct();
    }

    @Override
    public Stream<String> publicTodoStream(List<PublishTodo> todoList, long nid) {
        return todoList
                .stream()
                .map(todo -> noticeTodoService.getTodoAccountIdByNoticeId(
                        new NoticeTodoKey()
                                .withId(todo.getTid())
                                .withNid(nid),
                        todo.getFinish())
                )
                .flatMap(List::stream)
                .distinct();
    }

    @Override
    public Stream<String> publicInfoStream(List<PublishInfo> infoList) {
        return infoList
                .stream()
                .map(informationService::getAccountByInfo)
                .peek(response -> {
                    if (!response.isSuccess()) {
                        throw new RuntimeException("get info account fail: " + response.getMessage());
                    }
                })
                .map(Response::getData)
                .flatMap(List::stream)
                .distinct();
    }

    @Override
    public Stream<String> publicOrganizationStream(List<PublishOrganization> organizationList) {
        return Stream.concat(
                // 加入组织的成员
                organizationList
                        .stream()
                        .filter(PublishOrganization::getBelong)
                        .map(organization -> organizationService.getMemberId(organization.getOid()))
                        .peek(response -> {
                            if (!response.isSuccess()) {
                                throw new RuntimeException("get organization member fail:" + response.getMessage());
                            }
                        })
                        .flatMap(listResponse -> listResponse.getData().stream()),
                // 订阅组织的成员
                organizationList
                        .stream()
                        .filter(PublishOrganization::getSubscribe)
                        .map(organization -> organizationSubscribeService.getSubscribeAccountId(organization.getOid()))
                        .flatMap(List::stream)
        ).distinct();
    }
}
