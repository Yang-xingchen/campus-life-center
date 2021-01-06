package campuslifecenter.notice.model;


import campuslifecenter.notice.entry.AccountNoticeTodo;
import campuslifecenter.notice.entry.NoticeTodo;

import java.io.Serializable;

public class AccountTodo extends AccountNoticeTodo implements Serializable {

    private Integer type;
    private String value;

    public Integer getType() {
        return type;
    }

    public AccountTodo setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getValue() {
        return value;
    }

    public AccountTodo setValue(String value) {
        this.value = value;
        return this;
    }

    public AccountTodo setNoticeTodo(NoticeTodo noticeTodo) {
        setNid(noticeTodo.getNid());
        setId(noticeTodo.getId());
        setValue(noticeTodo.getTypeValue());
        setType(noticeTodo.getType());
        return this;
    }

    public AccountTodo setAccountNoticeTodo(AccountNoticeTodo accountNoticeTodo) {
        setId(accountNoticeTodo.getId());
        setNid(accountNoticeTodo.getNid());
        setAid(accountNoticeTodo.getAid());
        setAddList(accountNoticeTodo.getAddList());
        setTop(accountNoticeTodo.getTop());
        setFinish(accountNoticeTodo.getFinish());
        return this;
    }
}
