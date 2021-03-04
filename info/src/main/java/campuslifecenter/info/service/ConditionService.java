package campuslifecenter.info.service;

import campuslifecenter.info.entry.AccountSaveInfo;
import campuslifecenter.info.entry.ConditionInfo;

import java.util.List;

public interface ConditionService {

    void update(AccountSaveInfo accountTodo);

    List<String> select(long id, int type, String text);

    List<String> getAccounts(String ref);

    List<String> getAccounts(ConditionInfo info);

    String create(ConditionInfo conditionInfo);

    boolean publish(String ref);
}
