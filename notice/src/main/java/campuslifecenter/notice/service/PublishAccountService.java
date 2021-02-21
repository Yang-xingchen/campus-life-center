package campuslifecenter.notice.service;


import campuslifecenter.notice.entry.NoticeCondition;
import campuslifecenter.notice.model.PublishAccounts;

import java.util.List;
import java.util.stream.Stream;

public interface PublishAccountService {


    List<PublishAccounts> getPublishByNid(long id, boolean showName);

    Stream<PublishAccounts> publishAccountsStream(List<NoticeCondition> publishConditions, boolean showName);

    PublishAccounts publishAccounts(NoticeCondition condition, boolean showName);
}
