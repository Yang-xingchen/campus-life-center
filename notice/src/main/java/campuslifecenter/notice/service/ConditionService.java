package campuslifecenter.notice.service;


import campuslifecenter.notice.entry.AccountSubscribeKey;
import campuslifecenter.notice.entry.ConditionOrganization;

import java.util.List;

public interface ConditionService {

    void update(AccountSubscribeKey organization);

    List<String> getAccounts(String ref);

    List<String> getAccounts(ConditionOrganization conditionOrganization);

    String create(ConditionOrganization conditionOrganization);

    boolean publish(String ref);
}
