package campuslifecenter.todo.service.impl;

import campuslifecenter.todo.model.AccountInfo;
import campuslifecenter.todo.model.Response;
import campuslifecenter.todo.service.AccountService;
import campuslifecenter.todo.service.CacheService;
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
    private RedisTemplate<String, String> redisTemplate;
    @Value("${notice.cache.account-token}")
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
                    Response<AccountInfo> response = accountService.info(token);
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
                    Response<AccountInfo> response = accountService.infoById(id);
                    if (!response.isSuccess()) {
                        throw new IllegalArgumentException("account name not found:" + response.getMessage());
                    }
                    return response.getData().getName();
                });
    }

}
