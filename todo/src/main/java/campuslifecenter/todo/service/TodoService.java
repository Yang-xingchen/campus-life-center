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

    List<AccountTodoInfo> getTodoByRef(String source);

    List<AccountTodoInfo> getTodoByAccountAndRef(String aid, String source);

    boolean add(AddTodoRequest addBody);

    boolean updateAccount(String ref, List<String> aids);

    List<String> select(long id, boolean finish);

    List<Todo> getTodoByRefs(List<String> sources);
}
