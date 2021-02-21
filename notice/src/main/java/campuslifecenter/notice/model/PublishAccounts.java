package campuslifecenter.notice.model;

import campuslifecenter.notice.entry.NoticeCondition;

import java.io.Serializable;
import java.util.List;

public class PublishAccounts extends NoticeCondition {

    private List<IdName<String>> accounts;

    public PublishAccounts() {
    }

    public PublishAccounts setNoticeCondition(NoticeCondition condition) {
        setNid(condition.getNid());
        setRef(condition.getRef());
        setType(condition.getType());
        return this;
    }

    public List<IdName<String>> getAccounts() {
        return accounts;
    }

    public PublishAccounts setAccounts(List<IdName<String>> accounts) {
        this.accounts = accounts;
        return this;
    }
}
