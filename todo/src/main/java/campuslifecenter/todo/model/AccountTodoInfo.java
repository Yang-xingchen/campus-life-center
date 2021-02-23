package campuslifecenter.todo.model;


import campuslifecenter.todo.entry.AccountTodo;
import campuslifecenter.todo.entry.Todo;

public class AccountTodoInfo extends AccountTodo {

    private String accountName;
    private String value;
    private String ref;
    private Integer source;

    public AccountTodoInfo setTodo(Todo todo) {
        if (todo == null) {
            return this;
        }
        setId(todo.getId());
        setValue(todo.getContent());
        setRef(todo.getRef());
        setSource(todo.getType());
        return this;
    }

    public AccountTodoInfo setAccountNoticeTodo(AccountTodo accountTodo) {
        if (accountTodo == null) {
            return this;
        }
        setId(accountTodo.getId());
        setAid(accountTodo.getAid());
        setAddList(accountTodo.getAddList());
        setTop(accountTodo.getTop());
        setFinish(accountTodo.getFinish());
        return this;
    }

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

    public String getRef() {
        return ref;
    }

    public AccountTodoInfo setRef(String ref) {
        this.ref = ref;
        return this;
    }

    public Integer getSource() {
        return source;
    }

    public AccountTodoInfo setSource(Integer source) {
        this.source = source;
        return this;
    }
}
