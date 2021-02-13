package campuslifecenter.comment.service.impl;

import campuslifecenter.comment.service.AccountService;
import campuslifecenter.comment.service.CacheService;
import campuslifecenter.common.model.Response;
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
    private RedisTemplate<String, String> redisTemplate;
    @Value("${comment.redis.account-token}")
    public String TOKEN_PREFIX;
    @Value("${comment.redis.cache.account-name}")
    public String ACCOUNT_NAME_PREFIX;

    @Override
    @NewSpan("get aid")
    public String getAccountIdByToken(@SpanTag("token") String token) {
        return Optional
                .ofNullable(redisTemplate.opsForValue().get(TOKEN_PREFIX + token))
                .orElseGet(()->{
                    Response<AccountService.AccountInfo> response = accountService.info(token);
                    return response.checkGet(USER_CENTER, "account not found").getId();
                });
    }

    @Override
    @NewSpan("get account name")
    public String getAccountNameByID(@SpanTag("id") String id) {
        return Optional
                .ofNullable(redisTemplate.opsForValue().get(ACCOUNT_NAME_PREFIX + id))
                .orElseGet(()->{
                    Response<AccountService.AccountInfo> response = accountService.infoById(id);
                    return response.checkGet(USER_CENTER, "account name not found").getName();
                });
    }

}
