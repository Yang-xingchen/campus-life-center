package campuslifecenter.usercenter.service;

import campuslifecenter.usercenter.entry.AccountOrganization;

import java.util.List;

public interface ConditionService {

    void update(AccountOrganization organization);

    List<String> getAccounts(String ref);

    String create(List<String> aid);

    boolean publish(String ref);
}
