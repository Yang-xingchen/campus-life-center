package campuslifecenter.info.service;

import campuslifecenter.info.entry.AccountSaveInfo;

import java.util.List;

public interface ConditionService {

    void update(AccountSaveInfo accountTodo);

    List<String> select(long id, int type, String text);
}
