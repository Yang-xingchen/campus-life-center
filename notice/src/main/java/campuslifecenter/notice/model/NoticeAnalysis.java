package campuslifecenter.notice.model;

import campuslifecenter.notice.entry.*;

import java.io.Serializable;
import java.util.List;

public class NoticeAnalysis implements Serializable {

    private long nid;

    private List<AccountTodo> accountTodos;

    private List<AccountNotice> AccountNotice;
    private List<PublishAccount<?>> publishAccountList;

    public long getNid() {
        return nid;
    }

    public NoticeAnalysis setNid(long nid) {
        this.nid = nid;
        return this;
    }

    public List<AccountTodo> getAccountTodos() {
        return accountTodos;
    }

    public NoticeAnalysis setAccountTodos(List<AccountTodo> accountTodos) {
        this.accountTodos = accountTodos;
        return this;
    }

    public List<campuslifecenter.notice.entry.AccountNotice> getAccountNotice() {
        return AccountNotice;
    }

    public NoticeAnalysis setAccountNotice(List<campuslifecenter.notice.entry.AccountNotice> accountNotice) {
        AccountNotice = accountNotice;
        return this;
    }

    public List<PublishAccount<?>> getPublishAccountList() {
        return publishAccountList;
    }

    public NoticeAnalysis setPublishAccountList(List<PublishAccount<?>> publishAccountList) {
        this.publishAccountList = publishAccountList;
        return this;
    }
}
