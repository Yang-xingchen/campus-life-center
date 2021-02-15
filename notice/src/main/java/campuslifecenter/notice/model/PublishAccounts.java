package campuslifecenter.notice.model;

import java.io.Serializable;
import java.util.List;

public class PublishAccounts<SOURCE> implements Serializable {
    private String type;
    private SOURCE source;
    private List<IdName<String>> accounts;

    public PublishAccounts() {
    }

    public PublishAccounts(SOURCE source, List<IdName<String>> accounts) {
        this.type = source.getClass().getSimpleName();
        this.source = source;
        this.accounts = accounts;
    }

    public PublishAccounts(String type, SOURCE source, List<IdName<String>> accounts) {
        this.type = type;
        this.source = source;
        this.accounts = accounts;
    }

    public PublishAccounts<SOURCE> setType(String type) {
        this.type = type;
        return this;
    }

    public String getType() {
        return type;
    }

    public SOURCE getSource() {
        return source;
    }

    public PublishAccounts<SOURCE> setSource(SOURCE source) {
        this.type = source.getClass().getSimpleName();
        this.source = source;
        return this;
    }

    public List<IdName<String>> getAccounts() {
        return accounts;
    }

    public PublishAccounts<SOURCE> setAccounts(List<IdName<String>> accounts) {
        this.accounts = accounts;
        return this;
    }
}
