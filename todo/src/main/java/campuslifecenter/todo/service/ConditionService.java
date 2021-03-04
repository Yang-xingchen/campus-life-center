package campuslifecenter.todo.service;

import campuslifecenter.todo.entry.AccountTodo;
import campuslifecenter.todo.entry.ConditionTodo;

import java.util.List;

public interface ConditionService {

    void update(AccountTodo accountTodo);

    List<String> getAccounts(String ref);

    List<String> getAccounts(ConditionTodo todo);

    String create(ConditionTodo conditionTodo);

    boolean publish(String ref);
}
