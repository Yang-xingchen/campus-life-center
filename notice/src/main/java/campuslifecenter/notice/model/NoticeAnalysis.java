package campuslifecenter.notice.model;

import campuslifecenter.notice.entry.*;

import java.io.Serializable;
import java.util.List;

public class NoticeAnalysis implements Serializable {

    private long nid;

    private List<AccountNotice> AccountNotice;
    private List<PublishAccounts<?>> publishAccountsList;

    public long getNid() {
        return nid;
    }

    public NoticeAnalysis setNid(long nid) {
        this.nid = nid;
        return this;
    }

    public List<campuslifecenter.notice.entry.AccountNotice> getAccountNotice() {
        return AccountNotice;
    }

    public NoticeAnalysis setAccountNotice(List<campuslifecenter.notice.entry.AccountNotice> accountNotice) {
        AccountNotice = accountNotice;
        return this;
    }

    public List<PublishAccounts<?>> getPublishAccountList() {
        return publishAccountsList;
    }

    public NoticeAnalysis setPublishAccountList(List<PublishAccounts<?>> publishAccountsList) {
        this.publishAccountsList = publishAccountsList;
        return this;
    }
}
