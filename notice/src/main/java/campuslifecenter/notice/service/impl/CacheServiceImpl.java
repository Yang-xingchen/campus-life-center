package campuslifecenter.notice.service.impl;

import campuslifecenter.common.exception.ProcessException;
import campuslifecenter.common.model.Response;
import campuslifecenter.notice.service.AccountService;
import campuslifecenter.notice.service.CacheService;
import campuslifecenter.notice.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static campuslifecenter.common.exception.ProcessException.USER_CENTER;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${notice.redis.account-token}")
    public String TOKEN_PREFIX;
    @Value("${notice.redis.cache.account-name}")
    public String ACCOUNT_NAME_PREFIX;
    @Value("${notice.redis.cache.organization-name}")
    public String ORGANIZATION_NAME_PREFIX;

    @Override
    @NewSpan("get aid")
    public String getAccountIdByToken(@SpanTag("token") String token) {
        return Optional
                .ofNullable(redisTemplate.opsForValue().get(TOKEN_PREFIX + token))
                .orElseGet(()->{
                    Response<AccountService.AccountInfo> response = accountService.info(token);
                    ProcessException.check(USER_CENTER, "account not found", response);
                    return response.getData().getSignId();
                });
    }

    @Override
    @NewSpan("get account name")
    public String getAccountNameByID(@SpanTag("id") String id) {
        return Optional
                .ofNullable(redisTemplate.opsForValue().get(ACCOUNT_NAME_PREFIX + id))
                .orElseGet(()->{
                    Response<AccountService.AccountInfo> response = accountService.infoById(id);
                    ProcessException.check(USER_CENTER, "account name not found", response);
                    return response.getData().getName();
                });
    }

    @Override
    @NewSpan("get organization name")
    public String getOrganizationName(@SpanTag("id") int oid) {
        return Optional
                .ofNullable(redisTemplate
                        .opsForValue()
                        .get(ORGANIZATION_NAME_PREFIX + oid))
                .orElseGet(() -> {
                    Response<OrganizationService.Organization> response = organizationService
                            .getOrganization(oid);
                    ProcessException.check(USER_CENTER, "organization name not found", response);
                    return response.getData().getName();
                });
    }
}
