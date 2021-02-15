package campuslifecenter.notice.service;

import campuslifecenter.notice.entry.*;
import campuslifecenter.notice.model.PublishAccounts;
import campuslifecenter.notice.model.PublishNotice;

import java.util.List;
import java.util.stream.Stream;

public interface PublishAccountService {

    Stream<PublishAccounts<?>> publishAccountsStream(PublishNotice publishNotice, boolean getName);

    Stream<PublishAccounts<PublishAccountKey>> publishAccountStream(List<PublishAccountKey> accountList);

    Stream<PublishAccounts<PublishTodo>> publishTodoStream(List<PublishTodo> todoList);

    PublishAccounts<PublishTodo> publishTodo(PublishTodo todo);

    Stream<PublishAccounts<PublishInfo>> publishInfoStream(List<PublishInfo> infoList);

    PublishAccounts<PublishInfo> publishInfo(PublishInfo publishInfo);

    Stream<PublishAccounts<PublishOrganization>> publishOrganizationStream(List<PublishOrganization> organizationList);

    PublishAccounts<PublishOrganization> publishOrganization(PublishOrganization organization);

    List<PublishAccounts<?>> getPublishByNid(long nid, boolean getName);

    List<PublishAccountKey> getPublishAccountByNid(long nid);

    List<PublishTodo> getPublishTodoByNid(long nid);

    List<PublishInfo> getPublishInfoByNid(long nid);

    List<PublishOrganization> getPublishOrganizationByNid(long nid);

}
