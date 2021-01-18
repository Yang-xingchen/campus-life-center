package campuslifecenter.todo.service;


import campuslifecenter.todo.entry.AccountTodo;
import campuslifecenter.todo.entry.Todo;
import campuslifecenter.todo.model.AccountTodoInfo;
import campuslifecenter.todo.model.AddTodoRequest;

import java.util.List;


public interface TodoService {

    boolean update(AccountTodo accountTodo);

    List<AccountTodo> getTodoByAccount(String aid);

    Todo getTodoById(long id);

    List<AccountTodoInfo> getTodoBySource(String source);

    List<AccountTodoInfo> getTodoByAccountAndSource(String aid, String source);

    String add(AddTodoRequest addBody);

    List<String> select(long id, boolean finish);

    List<Todo> getTodoBySources(List<String> sources);
}
