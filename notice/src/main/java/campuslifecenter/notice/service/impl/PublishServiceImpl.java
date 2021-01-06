package campuslifecenter.notice.service.impl;

import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.mapper.PublishInfoMapper;
import campuslifecenter.notice.mapper.PublishOrganizationMapper;
import campuslifecenter.notice.mapper.PublishTodoMapper;
import campuslifecenter.notice.model.IdName;
import campuslifecenter.notice.model.PublishAccount;
import campuslifecenter.notice.model.PublishNotice;
import campuslifecenter.notice.model.Response;
import campuslifecenter.notice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class PublishServiceImpl implements PublishService {

    @Autowired
    private PublishTodoMapper publishTodoMapper;
    @Autowired
    private PublishInfoMapper publishInfoMapper;
    @Autowired
    private PublishOrganizationMapper publishOrganizationMapper;

    @Autowired
    private NoticeTodoService noticeTodoService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrganizationSubscribeService organizationSubscribeService;
    @Autowired
    private InformationService informationService;
    @Autowired
    private CacheService cacheService;

    @Override
    @SuppressWarnings("unchecked")
    public Stream<PublishAccount<?>> publicAccountStream(PublishNotice publishNotice) {
        return (Stream<PublishAccount<?>>) Stream.of(
                Stream.of(new PublishAccount<>().setAccounts(
                        publishNotice.getAccountList()
                                .stream()
                                .map(s -> new IdName<>(s, cacheService.getAccountNameByID(s)))
                                .collect(Collectors.toList())
                )),
                publicTodoStream(publishNotice.getTodoList()),
                publicInfoStream(publishNotice.getInfoList()),
                publicOrganizationStream(publishNotice.getOrganizationList())
        ).reduce(Stream::concat).get().distinct();
    }

    @Override
    public Stream<PublishAccount<PublishTodo>> publicTodoStream(List<PublishTodo> todoList) {
        return todoList
                .stream()
                .map(todo -> {
                    List<IdName<String>> accountIds = noticeTodoService.getTodoAccountIdByNoticeId(
                                new NoticeTodoKey()
                                        .withId(todo.getTid())
                                        .withNid(todo.getNid()),
                                todo.getFinish())
                            .stream()
                            .map(s -> new IdName<>(s, cacheService.getAccountNameByID(s)))
                            .collect(Collectors.toList());
                    return new PublishAccount<>(todo, accountIds);
                })
                .distinct();
    }

    @Override
    public Stream<PublishAccount<PublishInfo>> publicInfoStream(List<PublishInfo> infoList) {
        // return infoList
        //         .stream()
        //         .map(informationService::getAccountByInfo)
        //         .peek(response -> {
        //             if (!response.isSuccess()) {
        //                 throw new RuntimeException("get info account fail: " + response.getMessage());
        //             }
        //         })
        //         .map(Response::getData)
        //         .flatMap(List::stream)
        //         .distinct();
        // TODO
        return Stream.empty();
    }

    @Override
    public Stream<PublishAccount<PublishOrganization>> publicOrganizationStream(List<PublishOrganization> organizationList) {
        return organizationList
                .stream()
                .map(organization -> {
                    PublishAccount<PublishOrganization> publishAccount = new PublishAccount<>();
                    publishAccount.setSource(organization);
                    int oid = organization.getOid();
                    ArrayList<String> ids = new ArrayList<>();
                    if (organization.getBelong()) {
                        Response<List<String>> memberIds = organizationService.getMemberId(oid);
                        if (memberIds.isSuccess()) {
                            ids.addAll(memberIds.getData());
                        }
                    }
                    if (organization.getSubscribe()) {
                        ids.addAll(organizationSubscribeService.getSubscribeAccountId(oid));
                    }
                    publishAccount.setAccounts(ids
                            .stream()
                            .map(s->new IdName<>(s, cacheService.getAccountNameByID(s)))
                            .collect(Collectors.toList())
                    );
                    return publishAccount;
                });
    }

    @Override
    public List<PublishTodo> getPublishTodoByNid(long nid) {
        PublishTodoExample example = new PublishTodoExample();
        example.createCriteria().andNidEqualTo(nid);
        return publishTodoMapper
                .selectByExample(example);
    }

    @Override
    public List<PublishInfo> getPublishInfoByNid(long nid) {
        PublishInfoExample example = new PublishInfoExample();
        example.createCriteria().andNidEqualTo(nid);
        return publishInfoMapper
                .selectByExample(example);
    }

    @Override
    public List<PublishOrganization> getPublishOrganizationByNid(long nid) {
        PublishOrganizationExample example = new PublishOrganizationExample();
        example.createCriteria().andNidEqualTo(nid);
        return publishOrganizationMapper
                .selectByExample(example);
    }
}
