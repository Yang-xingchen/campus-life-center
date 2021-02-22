package campuslifecenter.notice.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "notice.condition")
public class PublishAccountsConfig {


    private Map<String, String> accountsMap;
    private Map<String, String> publishMap;

    public Map<String, String> getAccountsMap() {
        return accountsMap;
    }

    public PublishAccountsConfig setAccountsMap(Map<String, String> accountsMap) {
        this.accountsMap = accountsMap;
        return this;
    }

    public Map<String, String> getPublishMap() {
        return publishMap;
    }

    public PublishAccountsConfig setPublishMap(Map<String, String> publishMap) {
        this.publishMap = publishMap;
        return this;
    }
}
