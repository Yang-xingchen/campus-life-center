package campuslifecenter.notice.service.impl;

import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.model.Response;
import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.mapper.*;
import campuslifecenter.notice.model.IdName;
import campuslifecenter.notice.model.PublishAccounts;
import campuslifecenter.notice.model.PublishNotice;
import campuslifecenter.notice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static campuslifecenter.common.exception.ProcessException.*;

@Service
public class PublishAccountServiceImpl implements PublishAccountService {

    @Autowired
    private PublishAccountMapper publishAccountMapper;
    @Autowired
    private PublishTodoMapper publishTodoMapper;
    @Autowired
    private PublishInfoMapper publishInfoMapper;
    @Autowired
    private PublishOrganizationMapper publishOrganizationMapper;

    @Autowired
    private TodoService todoService;
    @Autowired
    private InformationService informationService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrganizationSubscribeService organizationSubscribeService;
    @Autowired
    private CacheService cacheService;

    @Autowired
    private TracerUtil tracerUtil;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<PublishAccounts<?>> getPublishByNid(long nid, boolean getName) {
        return Stream.of(
                publishAccountStream(getPublishAccountByNid(nid)),
                publishTodoStream(getPublishTodoByNid(nid)),
                publishInfoStream(getPublishInfoByNid(nid)),
                publishOrganizationStream(getPublishOrganizationByNid(nid))
        ).reduce(Stream::concat).get().peek(publishAccounts -> {
            if (getName) {
                publishAccounts.getAccounts().forEach(id -> {
                    id.setName(cacheService.getAccountNameByID(id.getId()));
                });
            }
        }).collect(Collectors.toList());
    }

    @Override
    @NewSpan("get account stream")
    @SuppressWarnings("unchecked")
    public Stream<PublishAccounts<?>> publishAccountsStream(PublishNotice publishNotice, boolean getName) {
        return (Stream<PublishAccounts<?>>) Stream.of(
                Stream.of(new PublishAccounts<>().setAccounts(
                        publishNotice.getAccountList()
                                .stream()
                                .filter(Objects::nonNull)
                                .map(s -> new IdName<>(s, null))
                                .collect(Collectors.toList())
                )),
                publishTodoStream(publishNotice.getTodoList()),
                publishInfoStream(publishNotice.getInfoList()),
                publishOrganizationStream(publishNotice.getOrganizationList())
        ).reduce(Stream::concat).get().peek(publishAccounts -> {
            if (getName) {
                publishAccounts.getAccounts().forEach(id -> {
                    id.setName(cacheService.getAccountNameByID(id.getId()));
                });
            }
        });
    }

    @Override
    public Stream<PublishAccounts<PublishAccountKey>> publishAccountStream(List<PublishAccountKey> accountList) {
        if (accountList.isEmpty()) {
            return Stream.of();
        }
        return tracerUtil.newSpan("account stream", span -> {
            return Stream.of(new PublishAccounts<>(accountList.get(0),
                    accountList.stream()
                            .map(PublishAccountKey::getAid)
                            .map(s -> new IdName<>(s, null))
                            .collect(Collectors.toList())
            ));
        });
    }

    @Override
    public Stream<PublishAccounts<PublishTodo>> publishTodoStream(List<PublishTodo> todoList) {
        return tracerUtil.newSpanRet("todo stream", span -> todoList.stream().map(this::publishTodo));
    }

    @Override
    public PublishAccounts<PublishTodo> publishTodo(PublishTodo todo) {
        return tracerUtil.newSpan("todo: " + todo.getTid(), span -> {
            span.tag("dynamic", todo.getDynamic() + "");
            span.tag("finish", todo.getFinish() + "");
            Response<List<String>> response = todoService.select(todo.getTid(), todo.getFinish());
            List<IdName<String>> accountIds = response.checkGet(TODO, "get todo fail")
                    .stream()
                    .distinct()
                    .map(s -> new IdName<>(s, null))
                    .collect(Collectors.toList());
            return new PublishAccounts<>(todo, accountIds);
        });
    }

    @Override
    public Stream<PublishAccounts<PublishInfo>> publishInfoStream(List<PublishInfo> infoList) {
        return tracerUtil.newSpanRet("info stream", span -> infoList.stream().map(this::publishInfo));
    }

    @Override
    public PublishAccounts<PublishInfo> publishInfo(PublishInfo info) {
        return tracerUtil.newSpan("info: " + info.getIid(), span -> {
            span.tag("dynamic", info.getDynamic() + "");
            Response<List<String>> response = informationService.select(info.getIid(), info.getType(), info.getText());
            List<IdName<String>> accountIds = response.checkGet(INFO, "get info fail")
                    .stream()
                    .distinct()
                    .map(s -> new IdName<>(s, null))
                    .collect(Collectors.toList());
            return new PublishAccounts<>(info, accountIds);
        });
    }

    @Override
    public Stream<PublishAccounts<PublishOrganization>> publishOrganizationStream(List<PublishOrganization> organizationList) {
        return tracerUtil.newSpanRet("organization", span -> organizationList.stream().map(this::publishOrganization));
    }

    @Override
    public PublishAccounts<PublishOrganization> publishOrganization(PublishOrganization organization) {
        int oid = organization.getOid();
        return tracerUtil.newSpan("organization: " + oid, span -> {
            PublishAccounts<PublishOrganization> publishAccounts = new PublishAccounts<>();
            publishAccounts.setSource(organization);
            span.tag("dynamic", organization.getDynamic() + "");
            span.tag("belong", organization.getBelong() + "");
            span.tag("subscribe", organization.getSubscribe() + "");
            ArrayList<String> ids = new ArrayList<>();
            if (organization.getBelong()) {
                Response<List<String>> response = organizationService.getMemberId(oid);
                ids.addAll(response.checkGet(USER_CENTER, "get member fail"));
            }
            if (organization.getSubscribe()) {
                ids.addAll(organizationSubscribeService.getSubscribeAccountId(oid));
            }
            publishAccounts.setAccounts(ids
                    .stream()
                    .distinct()
                    .map(s -> new IdName<>(s, null))
                    .collect(Collectors.toList())
            );
            return publishAccounts;
        });
    }

    @Override
    @NewSpan("get publish account list")
    public List<PublishAccountKey> getPublishAccountByNid(long nid) {
        PublishAccountExample example = new PublishAccountExample();
        example.createCriteria().andIdEqualTo(nid);
        return publishAccountMapper.selectByExample(example);
    }

    @Override
    @NewSpan("get todo account list")
    @Transactional(rollbackFor = RuntimeException.class)
    public List<PublishTodo> getPublishTodoByNid(@SpanTag("id") long nid) {
        PublishTodoExample example = new PublishTodoExample();
        example.createCriteria().andNidEqualTo(nid);
        return publishTodoMapper.selectByExample(example);
    }

    @Override
    @NewSpan("get info account list")
    @Transactional(rollbackFor = RuntimeException.class)
    public List<PublishInfo> getPublishInfoByNid(@SpanTag("id") long nid) {
        PublishInfoExample example = new PublishInfoExample();
        example.createCriteria().andNidEqualTo(nid);
        return publishInfoMapper.selectByExample(example);
    }

    @Override
    @NewSpan("get organization account list")
    @Transactional(rollbackFor = RuntimeException.class)
    public List<PublishOrganization> getPublishOrganizationByNid(@SpanTag("id") long nid) {
        PublishOrganizationExample example = new PublishOrganizationExample();
        example.createCriteria().andNidEqualTo(nid);
        return publishOrganizationMapper.selectByExample(example);
    }
}
