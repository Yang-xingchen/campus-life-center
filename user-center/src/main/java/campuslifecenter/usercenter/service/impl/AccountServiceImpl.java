package campuslifecenter.usercenter.service.impl;

import brave.ScopedSpan;
import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.usercenter.entry.*;
import campuslifecenter.usercenter.mapper.*;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.SignType;
import campuslifecenter.usercenter.service.AccountService;
import campuslifecenter.usercenter.service.EncryptionService;
import campuslifecenter.usercenter.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.MINUTES;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private SignInLogMapper signInLogMapper;

    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private TracerUtil tracerUtil;

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Value("${user-center.sign-in.test-count}")
    private int SIGN_IN_COUNT;
    @Value("${user-center.sign-in.redis.prefix}")
    private String UUID_PREFIX;
    @Value("${user-center.sign-in.redis.expire}")
    private int UUID_EXPIRE_NUMBER;
    private TimeUnit UUID_EXPIRE_UNIT;
    @Value("${user-center.token.redis.prefix}")
    private String TOKEN_PREFIX;
    @Value("${user-center.token.redis.expire}")
    private int TOKEN_EXPIRE_NUMBER;
    private TimeUnit TOKEN_EXPIRE_UNIT;

    @Value("${user-center.cache.account-name}")
    public String ACCOUNT_NAME_PREFIX;

    @Autowired
    public AccountServiceImpl(@Value("${user-center.sign-in.redis.expire-unit}") String uuidUnit,
                              @Value("${user-center.token.redis.expire-unit}") String tokenUnit) {
        this.UUID_EXPIRE_UNIT = TimeUnit.valueOf(uuidUnit.toUpperCase());
        this.TOKEN_EXPIRE_UNIT = TimeUnit.valueOf(tokenUnit.toUpperCase());
    }

    @Override
    public String signInId() {
        String uuid = UUID.randomUUID().toString();
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(UUID_PREFIX + uuid,
                Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        redisAtomicInteger.set(0);
        redisAtomicInteger.expire(UUID_EXPIRE_NUMBER, UUID_EXPIRE_UNIT);
        return uuid;
    }

    @Override
    @NewSpan("sign in")
    public boolean signIn(@SpanTag("id") String aid, String pwd, SignInLog sign) {
        pwd = encryptionService.rsaDecode(pwd);
        tracerUtil.getSpan().tag("aid", aid);

        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(UUID_PREFIX + sign.getToken(),
                Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        // 违法登录id
        try {
            redisAtomicInteger.get();
        } catch (DataRetrievalFailureException e) {
            throw new ResponseException("未知登录id", e, 501);
        }
        // 尝试登录次数过多
        if (redisAtomicInteger.incrementAndGet() > SIGN_IN_COUNT) {
            throw new ResponseException("尝试次数过多", 502);
        }
        // 重复登录
        BoundValueOperations<String, String> tokenValueOps = redisTemplate.boundValueOps(TOKEN_PREFIX + sign.getToken());
        if (!Objects.equals(tokenValueOps.setIfAbsent("", 1, MINUTES), true)) {
            return true;
        }
        // 账户不存在
        Account account = accountMapper.selectByPrimaryKey(aid);
        if (account == null) {
            redisTemplate.delete(TOKEN_PREFIX + sign.getToken());
            throw new ResponseException("账户名或密码错误", 301);
        }
        // 密码错误
        if (!PASSWORD_ENCODER.matches(pwd, account.getPassword())) {
            redisTemplate.delete(TOKEN_PREFIX + sign.getToken());
            throw new ResponseException("账户名或密码错误", 302);
        }
        // 下线已登录
        signOut(aid, SignType.SIGN_IN, new Date());
        // 登录
        int count = signInLogMapper.insert(sign);
        redisTemplate.delete(UUID_PREFIX + sign.getToken());
        if (count != 1) {
            throw new ResponseException("未知错误");
        }
        tokenValueOps.set(aid);
        tokenValueOps.expire(TOKEN_EXPIRE_NUMBER, TOKEN_EXPIRE_UNIT);
        return true;
    }

    @Override
    @NewSpan("sign out")
    public boolean signOut(@SpanTag("id") String aid) {
        signOut(aid, SignType.SIGN_OUT, new Date());
        return true;
    }

    /**
     * 退出登录，调用方法需自行实现事务管理
     * @param aid 账户id
     * @param signType 退出类型
     * @param now 时间
     */
    private void signOut(String aid, SignType signType, Date now) {
        SignInLogExample example = new SignInLogExample();
        example.createCriteria()
                .andAidEqualTo(aid)
                .andSignOutTimeIsNull();
        signInLogMapper.selectByExample(example)
                .forEach(signed -> {
                    signed.setType(signType.getCode());
                    signed.setSignOutTime(now);
                    signInLogMapper.updateByPrimaryKey(signed);
                    redisTemplate.delete(TOKEN_PREFIX + signed.getToken());
                });
    }

    @Override
    @NewSpan("check")
    public boolean checkToken(@SpanTag("token") String token) {
        if (Objects.equals(redisTemplate.hasKey(TOKEN_PREFIX + token), true)) {
            return true;
        }
        List<SignInLog> signIns = tracerUtil.newSpan("get sign in log", span -> {
            SignInLogExample example = new SignInLogExample();
            example.createCriteria()
                    .andTokenEqualTo(token)
                    .andSignOutTimeIsNull();
            return signInLogMapper.selectByExample(example);
        });
        // 未登录
        if (signIns.size() == 0) {
            return false;
        } else if (signIns.size() > 1) {
            throw new IllegalStateException("多个已登录状态");
        }
        Date now = new Date();
        SignInLog signInLog = signIns.get(0);
        boolean success = tracerUtil.newSpan("sign out", span -> {
            Date timeOut = new Date(now.getTime() + TOKEN_EXPIRE_UNIT.toMillis(TOKEN_EXPIRE_NUMBER));
            if (signInLog.getSignInTime().after(timeOut)) {
                signInLog.setType(SignType.TIME_OUT.getCode());
                signInLog.setSignOutTime(now);
                signInLogMapper.updateByPrimaryKey(signInLog);
                return false;
            }
            return true;
        });
        if (!success) {
            return false;
        }
        tracerUtil.newSpan("sign in", span -> {
            signInLog.setType(SignType.UPDATE.getCode());
            signInLog.setSignOutTime(now);
            signInLogMapper.updateByPrimaryKey(signInLog);
            SignInLog signIn = new SignInLog()
                    .withToken(signInLog.getToken())
                    .withIp(signInLog.getIp())
                    .withSource(signInLog.getSource());
            signIn.setAid(signInLog.getAid());
            signIn.setSignInTime(now);
            signInLogMapper.insert(signIn);
            redisTemplate.opsForValue().set(TOKEN_PREFIX + token, signIn.getAid(),
                    TOKEN_EXPIRE_NUMBER, TOKEN_EXPIRE_UNIT);
        });
        return true;
    }

    @Override
    @NewSpan("get info")
    public AccountInfo getAccountInfo(@SpanTag("token") String token) {
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(TOKEN_PREFIX + token);
        String aid = ops.get();
        if (aid == null || "".equals(aid)) {
            if (checkToken(token)) {
                aid = ops.get();
            } else {
                return null;
            }
        }
        String finalAid = aid;
        return tracerUtil.newSpan("get account",
                (Function<ScopedSpan, AccountInfo>) span -> getAccount(finalAid).setToken(token));
    }

    @Override
    @NewSpan("get account")
    public AccountInfo getAccount(@SpanTag("id") String id) {
        return Optional.ofNullable(accountMapper.selectByPrimaryKey(id))
                .map(AccountInfo::withAccount)
                .map(account -> tracerUtil.newSpan("write name cache", span -> {
                    redisTemplate.opsForValue()
                            .set(ACCOUNT_NAME_PREFIX + account.getSignId(), account.getName(),
                                    1, DAYS);
                    tracerUtil.getSpan().tag("aid", account.getSignId());
                    return account;
                }))
                .map(account -> tracerUtil.newSpan("set organizations",
        (Function<ScopedSpan, AccountInfo>) span -> account.setOrganizations(organizationService.getOrganization(id))))
                .orElse(null);
    }

    @Override
    @NewSpan("add account list")
    public Map<Boolean, List<Account>> addAllAccount(List<Account> accounts) {
        return accounts
                .stream()
                .peek(account -> {
                    if (account.getSecurityKey() == null) {
                        account.withSecurityKey(account.getPassword());
                    }
                })
                .collect(Collectors.partitioningBy(account -> accountMapper.insert(account) != 1));
    }

    @Override
    @NewSpan("get all account")
    public List<AccountInfo> findAllAccount() {
        return accountMapper
                .selectByExample(new AccountExample())
                .stream()
                .map(AccountInfo::withAccount)
                .peek(accountInfo -> accountInfo.setOrganizations(organizationService.getOrganization(accountInfo.getSignId())))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, ?> actuatorAccount() {
        SignInLogExample example = new SignInLogExample();
        example.createCriteria().andSignOutTimeIsNull();
        List<SignInLog> signInLogs = signInLogMapper.selectByExample(example);
        return Map.of(
                "total", accountMapper.countByExample(new AccountExample()),
                "on-line-count", signInLogs.size(),
                "on-line", signInLogs
        );
    }

    @Override
    @NewSpan("get account list")
    public List<AccountInfo> getAccountInfos(List<String> ids) {
        AccountExample example = new AccountExample();
        example.createCriteria().andSignIdIn(ids);
        return accountMapper
                .selectByExample(example)
                .stream()
                .map(AccountInfo::withAccount)
                .collect(Collectors.toList());
    }

}
