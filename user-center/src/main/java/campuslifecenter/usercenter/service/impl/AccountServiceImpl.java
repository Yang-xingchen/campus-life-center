package campuslifecenter.usercenter.service.impl;

import brave.ScopedSpan;
import campuslifecenter.common.component.TracerUtil;
import campuslifecenter.common.exception.ResponseException;
import campuslifecenter.usercenter.entry.*;
import campuslifecenter.usercenter.mapper.*;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.SignType;
import campuslifecenter.usercenter.model.UpdateAccount;
import campuslifecenter.usercenter.service.AccountService;
import campuslifecenter.usercenter.service.EncryptionService;
import campuslifecenter.usercenter.service.OrganizationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;
import java.util.concurrent.CountDownLatch;
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

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private TracerUtil tracerUtil;

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Value("${user-center.redis.cache.account-info}")
    private String ACCOUNT_INFO;
    @Value("${user-center.sign-in.test-count}")
    private int SIGN_IN_COUNT;
    @Value("${user-center.redis.sign-in}")
    private String UUID_PREFIX;
    @Value("${user-center.sign-in.expire}")
    private int UUID_EXPIRE_NUMBER;
    private TimeUnit UUID_EXPIRE_UNIT;
    @Value("${user-center.redis.token}")
    private String TOKEN_PREFIX;
    @Value("${user-center.token.expire}")
    private int TOKEN_EXPIRE_NUMBER;
    private TimeUnit TOKEN_EXPIRE_UNIT;

    @Value("${user-center.redis.cache.account-name}")
    public String ACCOUNT_NAME_PREFIX;

    @Autowired
    public AccountServiceImpl(@Value("${user-center.sign-in.expire-unit}") String uuidUnit,
                              @Value("${user-center.token.expire-unit}") String tokenUnit) {
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
        // 登录
        sign.setSource(0);
        int count = signInLogMapper.insertSelective(sign);
        redisTemplate.delete(UUID_PREFIX + sign.getToken());
        if (count != 1) {
            throw new ResponseException("未知错误");
        }
        tokenValueOps.set(aid);
        tokenValueOps.expire(TOKEN_EXPIRE_NUMBER, TOKEN_EXPIRE_UNIT);
        return true;
    }

    @Override
    @NewSpan("sign in")
    public boolean signInByToken(@SpanTag("token") String token, SignInLog sign) {
        AccountInfo accountInfo = getAccountInfo(token);
        sign.setAid(accountInfo.getId());
        sign.setSignInTime(new Date());
        sign.setSignOutTime(null);
        sign.setSource(1);
        sign.setToken(null);
        // 登录
        int count = signInLogMapper.insertSelective(sign);
        if (count != 1) {
            throw new ResponseException("未知错误");
        }
        BoundValueOperations<String, String> tokenValueOps = redisTemplate.boundValueOps(TOKEN_PREFIX + sign.getToken());
        tokenValueOps.set(sign.getAid());
        tokenValueOps.expire(TOKEN_EXPIRE_NUMBER, TOKEN_EXPIRE_UNIT);
        return true;
    }

    @Override
    @NewSpan("sign out")
    public boolean signOut(@SpanTag("id") String aid, @SpanTag("token") String token) {
        SignInLogExample example = new SignInLogExample();
        Date now = new Date();
        if (token == null) {
            example.createCriteria()
                    .andAidEqualTo(aid)
                    .andSignOutTimeIsNull();
        } else {
            example.createCriteria()
                    .andAidEqualTo(aid)
                    .andSignOutTimeIsNull()
                    .andTokenEqualTo(token);
        }
        signInLogMapper.selectByExample(example)
                .forEach(signed -> {
                    signed.setType(SignType.SIGN_OUT.getCode());
                    signed.setSignOutTime(now);
                    signInLogMapper.updateByPrimaryKey(signed);
                    redisTemplate.delete(TOKEN_PREFIX + signed.getToken());
                });
        return true;
    }

    @Override
    @NewSpan("sign out")
    public boolean signOutOther(@SpanTag("id") String aid, @SpanTag("token") String token) {
        SignInLogExample example = new SignInLogExample();
        Date now = new Date();
        example.createCriteria().andAidEqualTo(aid).andSignOutTimeIsNull().andTokenNotEqualTo(token);
        signInLogMapper.selectByExample(example)
                .forEach(signed -> {
                    signed.setType(SignType.SIGN_OUT.getCode());
                    signed.setSignOutTime(now);
                    signInLogMapper.updateByPrimaryKey(signed);
                    redisTemplate.delete(TOKEN_PREFIX + signed.getToken());
                });
        return true;
    }

    @Override
    @NewSpan("sign in logs")
    public List<SignInLog> signInLogs(String id) {
        SignInLogExample example = new SignInLogExample();
        example.createCriteria().andAidEqualTo(id);
        return signInLogMapper.selectByExample(example);
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
        tracerUtil.getSpan().tag("account", aid);
        return tracerUtil.newSpanRet("get account", span -> Optional.ofNullable(getAccount(finalAid)).map(accountInfo -> accountInfo.setToken(token)).orElse(null));
    }

    @Override
    @NewSpan("get account")
    public AccountInfo getAccount(@SpanTag("id") String id) {
        BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(ACCOUNT_INFO + id);
        String cache = ops.get();
        if (cache != null) {
            try {
                return objectMapper.readValue(cache, AccountInfo.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return Optional.ofNullable(accountMapper.selectByPrimaryKey(id))
                .map(AccountInfo::withAccount)
                .map(account -> tracerUtil.newSpan("write name cache", span -> {
                    redisTemplate.opsForValue()
                            .set(ACCOUNT_NAME_PREFIX + account.getId(), account.getName(),
                                    1, DAYS);
                    tracerUtil.getSpan().tag("aid", account.getId());
                    return account;
                }))
                .map(account -> tracerUtil.newSpanRet("set organizations",
                        span -> account.setOrganizations(organizationService.getOrganization(id))))
                .map(account -> tracerUtil.newSpan("write cache", span -> {
                    try {
                        ops.set(objectMapper.writeValueAsString(account), 1, DAYS);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return account;
                }))
                .orElse(null);
    }

    @Override
    @NewSpan("add account list")
    public Map<Boolean, List<Account>> addAllAccount(List<Account> accounts) {
        CountDownLatch countDownLatch = new CountDownLatch(accounts.size());
        Date now = new Date();
        accounts.forEach(account -> tracerUtil.newSpanAsync("set password", countDownLatch, scopedSpan -> {
            account.setPassword(PASSWORD_ENCODER.encode(account.getPassword()));
            account.withSecurityKey(account.getPassword());
            account.setCreateData(now);
        }));
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Map<Boolean, List<Account>> collect = accounts
                .stream()
                .collect(Collectors.partitioningBy(account -> {
                    try {
                        return accountMapper.insertSelective(account) == 1;
                    } catch (RuntimeException e) {
                        return false;
                    }
                }));
        if (!collect.get(Boolean.FALSE).isEmpty()) {
            // 回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } else {
            organizationService.addAccount(accounts.stream().map(Account::getId).collect(Collectors.toList()));
        }
        return collect;
    }

    @Override
    @NewSpan("get all account")
    public List<AccountInfo> findAllAccount() {
        return accountMapper
                .selectByExample(new AccountExample())
                .stream()
                .map(AccountInfo::withAccount)
                .peek(accountInfo -> accountInfo.setOrganizations(organizationService.getOrganization(accountInfo.getId())))
                .collect(Collectors.toList());
    }

    @Override
    @NewSpan("get account list")
    public List<AccountInfo> getAccountInfos(List<String> ids) {
        AccountExample example = new AccountExample();
        example.createCriteria().andIdIn(ids);
        return accountMapper
                .selectByExample(example)
                .stream()
                .map(AccountInfo::withAccount)
                .collect(Collectors.toList());
    }

    @Override
    public boolean update(UpdateAccount updateAccount) {
        String aid = updateAccount.getId();
        if (updateAccount.getPassword() != null) {
            String newPwd = encryptionService.rsaDecode(updateAccount.getPassword());
            String oldPwd = encryptionService.rsaDecode(updateAccount.getOriginalPassword());
            Account oldAccount = accountMapper.selectByPrimaryKey(aid);
            if (!PASSWORD_ENCODER.matches(oldPwd, oldAccount.getPassword())) {
                return false;
            }
            updateAccount.setPassword(PASSWORD_ENCODER.encode(newPwd));
        }
        updateAccount.setCreateData(null);
        if (accountMapper.updateByPrimaryKeySelective(updateAccount) == 1) {
            redisTemplate.delete(ACCOUNT_INFO + aid);
            redisTemplate.delete(ACCOUNT_NAME_PREFIX + aid);
            return true;
        }
        return false;
    }

}
