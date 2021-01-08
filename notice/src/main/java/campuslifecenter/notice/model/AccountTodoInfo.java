package campuslifecenter.notice.model;


import java.io.Serializable;

public class AccountTodoInfo implements Serializable {
    private Long id;
    private String aid;
    private String accountName;
    private String value;
    private String source;
    private Boolean finish;
    private Boolean top;
    private Boolean addList;

    public Long getId() {
        return id;
    }

    public AccountTodoInfo setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAid() {
        return aid;
    }

    public AccountTodoInfo setAid(String aid) {
        this.aid = aid;
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

    public String getSource() {
        return source;
    }

    public AccountTodoInfo setSource(String source) {
        this.source = source;
        return this;
    }

    public Boolean getFinish() {
        return finish;
    }

    public AccountTodoInfo setFinish(Boolean finish) {
        this.finish = finish;
        return this;
    }

    public Boolean getTop() {
        return top;
    }

    public AccountTodoInfo setTop(Boolean top) {
        this.top = top;
        return this;
    }

    public Boolean getAddList() {
        return addList;
    }

    public AccountTodoInfo setAddList(Boolean addList) {
        this.addList = addList;
        return this;
    }
}
