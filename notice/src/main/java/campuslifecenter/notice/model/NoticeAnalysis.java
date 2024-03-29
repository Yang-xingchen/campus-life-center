package campuslifecenter.notice.model;

import campuslifecenter.notice.entry.*;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class NoticeAnalysis implements Serializable {

    private long nid;

    @ApiModelProperty("用户操作")
    private List<AccountNotice> AccountNotice;
    @ApiModelProperty("发布方式及成员")
    private List<PublishAccounts> publishAccountsList;

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

    public List<PublishAccounts> getPublishAccountsList() {
        return publishAccountsList;
    }

    public NoticeAnalysis setPublishAccountsList(List<PublishAccounts> publishAccountsList) {
        this.publishAccountsList = publishAccountsList;
        return this;
    }
}
