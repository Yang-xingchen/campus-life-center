package campuslifecenter.todo.model;


import campuslifecenter.todo.entry.AccountTodo;
import campuslifecenter.todo.entry.Todo;

public class AccountTodoInfo extends AccountTodo {

    private String accountName;
    private String value;
    private String source;

    public String getAccountName() {
        return accountName;
    }

    public AccountTodoInfo setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public String getValue() {
        return value;
    }

    public AccountTodoInfo setValue(String value) {
        this.value = value;
        return this;
    }

    public String getSource() {
        return source;
    }

    public AccountTodoInfo setSource(String source) {
        this.source = source;
        return this;
    }

    public AccountTodoInfo setTodo(Todo todo) {
        setId(todo.getId());
        setValue(todo.getContent());
        setSource(todo.getRef());
        return this;
    }

    public AccountTodoInfo setAccountNoticeTodo(AccountTodo accountTodo) {
        setId(accountTodo.getId());
        setAid(accountTodo.getAid());
        setAddList(accountTodo.getAddList());
        setTop(accountTodo.getTop());
        setFinish(accountTodo.getFinish());
        return this;
    }
}
