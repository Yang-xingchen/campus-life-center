package campuslifecenter.notice.service.impl;

import campuslifecenter.notice.model.Response;
import campuslifecenter.notice.service.AccountService;
import campuslifecenter.notice.service.CacheService;
import campuslifecenter.notice.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${notice.account-token}")
    public String TOKEN_PREFIX;
    @Value("${notice.cache.account-name}")
    public String ACCOUNT_NAME_PREFIX;
    @Value("${notice.cache.organization-name}")
    public String ORGANIZATION_NAME_PREFIX;

    @Override
    public String getAccountIdByToken(String token) {
        return Optional
                .ofNullable(redisTemplate.opsForValue().get(TOKEN_PREFIX + token))
                .orElseGet(()->{
                    Response<AccountService.AccountInfo> response = accountService.info(token);
                    if (!response.isSuccess()) {
                        throw new IllegalArgumentException("account not found:" + response.getMessage());
                    }
                    return response.getData().getSignId();
                });
    }

    @Override
    public String getAccountNameByID(String id) {
        return Optional
                .ofNullable(redisTemplate.opsForValue().get(ACCOUNT_NAME_PREFIX + id))
                .orElseGet(()->{
                    Response<AccountService.AccountInfo> response = accountService.infoById(id);
                    if (!response.isSuccess()) {
                        throw new IllegalArgumentException("account name not found:" + response.getMessage());
                    }
                    return response.getData().getName();
                });
    }

    @Override
    public String getOrganizationName(int oid) {
        return Optional
                .ofNullable(redisTemplate
                        .opsForValue()
                        .get(ORGANIZATION_NAME_PREFIX + oid))
                .orElseGet(() -> {
                    Response<OrganizationService.Organization> response = organizationService
                            .getOrganization(oid);
                    if (!response.isSuccess()) {
                        throw new RuntimeException("organization not found:" + response.getMessage());
                    }
                    return response.getData().getName();
                });
    }
}
