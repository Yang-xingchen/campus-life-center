package campuslifecenter.notice.model;


public class AccountTodoInfo extends TodoInfo {
    private String aid;
    private String accountName;
    private Boolean finish;
    private Boolean top;
    private Boolean addList;

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
