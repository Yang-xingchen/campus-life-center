package campuslifecenter.notice.model;

import java.io.Serializable;
import java.util.List;

public class PublishAccount<SOURCE> implements Serializable {
    private String type;
    private SOURCE source;
    private List<IdName<String>> accounts;

    public PublishAccount() {
    }

    public PublishAccount(SOURCE source, List<IdName<String>> accounts) {
        this.type = source.getClass().getSimpleName();
        this.source = source;
        this.accounts = accounts;
    }

    public PublishAccount(String type, SOURCE source, List<IdName<String>> accounts) {
        this.type = type;
        this.source = source;
        this.accounts = accounts;
    }

    public PublishAccount<SOURCE> setType(String type) {
        this.type = type;
        return this;
    }

    public String getType() {
        return type;
    }

    public SOURCE getSource() {
        return source;
    }

    public PublishAccount<SOURCE> setSource(SOURCE source) {
        this.type = source.getClass().getSimpleName();
        this.source = source;
        return this;
    }

    public List<IdName<String>> getAccounts() {
        return accounts;
    }

    public PublishAccount<SOURCE> setAccounts(List<IdName<String>> accounts) {
        this.accounts = accounts;
        return this;
    }
}
