package campuslifecenter.todo.service;

import campuslifecenter.todo.entry.AccountTodo;
import campuslifecenter.todo.entry.ConditionTodo;

import java.util.List;

public interface ConditionService {

    void update(AccountTodo accountTodo);

    List<String> getAccounts(String ref);

    String create(ConditionTodo conditionTodo);
}
