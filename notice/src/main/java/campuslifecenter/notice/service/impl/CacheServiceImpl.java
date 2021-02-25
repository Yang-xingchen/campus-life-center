package campuslifecenter.notice.service.impl;

import campuslifecenter.common.exception.ProcessException;
import campuslifecenter.common.model.Response;
import campuslifecenter.notice.service.AccountService;
import campuslifecenter.notice.service.CacheService;
import campuslifecenter.notice.service.InformationService;
import campuslifecenter.notice.service.OrganizationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private InformationService informationService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${notice.redis.account-token}")
    public String TOKEN_PREFIX;
    @Value("${notice.redis.cache.account-name}")
    public String ACCOUNT_NAME_PREFIX;
    @Value("${notice.redis.cache.organization-name}")
    public String ORGANIZATION_NAME_PREFIX;
    @Value("${notice.redis.cache.collect-name}")
    public String COLLECT_NAME_PREFIX;
    @Value("${notice.redis.cache.account-info}")
    private String ACCOUNT_INFO;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    @NewSpan("get account info")
    public AccountService.AccountInfo getAccountInfo(String aid) {
        return Optional
                .ofNullable(redisTemplate.opsForValue().get(ACCOUNT_INFO + aid))
                .map(s -> {
                    try {
                        return OBJECT_MAPPER.readValue(s, AccountService.AccountInfo.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .orElseGet(() -> accountService.infoById(aid).checkGet(USER_CENTER, "get info fail"));
    }

    @Override
    @NewSpan("get aid")
    public String getAccountIdByToken(@SpanTag("token") String token) {
        return Optional
                .ofNullable(redisTemplate.opsForValue().get(TOKEN_PREFIX + token))
                .orElseGet(()->{
                    Response<AccountService.AccountInfo> response = accountService.info(token);
                    AccountService.AccountInfo data = response.getData();
                    if (data == null) {
                        return "";
                    }
                    return data.getId();
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
                    return response.checkGet(USER_CENTER, "organization name not found").getName();
                });
    }

    @Override
    @NewSpan("get collect name")
    public String getCollectName(@SpanTag("ref") String ref) {
        return Optional
                .ofNullable(redisTemplate.opsForValue().get(COLLECT_NAME_PREFIX + ref))
                .orElseGet(() -> {
                    Response<String> response = informationService.getRefName(ref);
                    return response.checkGet(ProcessException.INFO,"get info fail");
                });
    }
}
