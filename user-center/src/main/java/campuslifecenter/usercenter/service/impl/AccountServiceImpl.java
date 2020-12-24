package campuslifecenter.usercenter.service.impl;

import campuslifecenter.usercenter.entry.*;
import campuslifecenter.usercenter.mapper.*;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.AccountOrganizationInfo;
import campuslifecenter.usercenter.model.SignInType;
import campuslifecenter.usercenter.model.SignType;
import campuslifecenter.usercenter.service.AccountService;
import campuslifecenter.usercenter.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static campuslifecenter.usercenter.model.SignInType.*;
import static java.util.concurrent.TimeUnit.MINUTES;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class AccountServiceImpl implements AccountService {

    private AccountMapper accountMapper;
    private SignInLogMapper signInLogMapper;
    private AccountOrganizationMapper accountOrganizationMapper;
    private OrganizationMapper organizationMapper;

    private EncryptionService encryptionService;

    private RedisTemplate<String, String> redisTemplate;

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    private final int SIGN_IN_COUNT;
    private final String UUID_PREFIX;
    private final int UUID_EXPIRE_NUMBER;
    private final TimeUnit UUID_EXPIRE_UNIT;
    private final String TOKEN_PREFIX;
    private final int TOKEN_EXPIRE_NUMBER;
    private final TimeUnit TOKEN_EXPIRE_UNIT;

    @Autowired
    public AccountServiceImpl(AccountMapper accountMapper,
                              SignInLogMapper signInLogMapper,
                              AccountOrganizationMapper accountOrganizationMapper,
                              OrganizationMapper organizationMapper,
                              RedisTemplate<String, String> redisTemplate,
                              EncryptionService encryptionService,
                              @Value("${user-center.sign-in.test-count}") int signInCount,
                              @Value("${user-center.sign-in.redis.prefix}") String uuidPrefix,
                              @Value("${user-center.sign-in.redis.expire}") int uuidExpire,
                              @Value("${user-center.sign-in.redis.expire-unit}") String uuidUnit,
                              @Value("${user-center.token.redis.prefix}") String tokenPrefix,
                              @Value("${user-center.token.redis.expire}") int tokenExpire,
                              @Value("${user-center.token.redis.expire-unit}") String tokenUnit) {
        this.accountMapper = accountMapper;
        this.signInLogMapper = signInLogMapper;
        this.accountOrganizationMapper = accountOrganizationMapper;
        this.organizationMapper = organizationMapper;
        this.redisTemplate = redisTemplate;
        this.encryptionService = encryptionService;

        this.SIGN_IN_COUNT = signInCount;
        this.UUID_PREFIX = uuidPrefix;
        this.UUID_EXPIRE_NUMBER = uuidExpire;
        this.UUID_EXPIRE_UNIT = TimeUnit.valueOf(uuidUnit.toUpperCase());
        this.TOKEN_PREFIX = tokenPrefix;
        this.TOKEN_EXPIRE_NUMBER = tokenExpire;
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
    public SignInType signIn(String aid, String pwd, SignInLog sign) {
        pwd = encryptionService.rsaDecode(pwd);

        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(UUID_PREFIX + sign.getToken(),
                Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        // 违法登录id
        try {
            redisAtomicInteger.get();
        } catch (DataRetrievalFailureException e) {
            return UNKNOWN_SING_IN_ID;
        }
        // 尝试登录次数过多
        if (redisAtomicInteger.incrementAndGet() > SIGN_IN_COUNT) {
            return TEST_SIGN_IN_TOO_MUCH;
        }
        // 重复登录
        BoundValueOperations<String, String> tokenValueOps = redisTemplate.boundValueOps(TOKEN_PREFIX + sign.getToken());
        if (!Objects.equals(tokenValueOps.setIfAbsent("", 1, MINUTES), true)) {
            if ("".equals(tokenValueOps.get())) {
                return REPEAT;
            } else {
                return ALREADY_SIGN_IN;
            }
        }
        // 账户不存在
        Account account = accountMapper.selectByPrimaryKey(aid);
        if (account == null) {
            redisTemplate.delete(TOKEN_PREFIX + sign.getToken());
            return ACCOUNT_NOT_EXIST;
        }
        // 密码错误
        if (!PASSWORD_ENCODER.matches(pwd, account.getPassword())) {
            redisTemplate.delete(TOKEN_PREFIX + sign.getToken());
            return PASSWORD_ERROR;
        }
        // 下线已登录
        signOut(aid, SignType.SIGN_IN, new Date());
        // 登录
        int count = signInLogMapper.insert(sign);
        redisTemplate.delete(UUID_PREFIX + sign.getToken());
        if (count != 1) {
            return UNKNOWN;
        }
        tokenValueOps.set(aid);
        tokenValueOps.expire(TOKEN_EXPIRE_NUMBER, TOKEN_EXPIRE_UNIT);
        return SUCCESS;
    }

    @Override
    public boolean signOut(String aid) {
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
    public boolean checkToken(String token) {
        // 查询redis
        if (Objects.equals(redisTemplate.hasKey(TOKEN_PREFIX + token), true)) {
            return true;
        }
        // 查询数据库
        SignInLogExample example = new SignInLogExample();
        example.createCriteria()
                .andTokenEqualTo(token)
                .andSignOutTimeIsNull();
        List<SignInLog> signIns = signInLogMapper.selectByExample(example);
        if (signIns.size() == 0) {
            return false;
        } else if (signIns.size() > 1) {
            throw new IllegalStateException("多个已登录状态");
        }
        // 退出已登录
        Date now = new Date();
        SignInLog signInLog = signIns.get(0);
        if (signInLog.getSignInTime().after(new Date(now.getTime() + TOKEN_EXPIRE_UNIT.toMillis(TOKEN_EXPIRE_NUMBER)))) {
            signInLog.setType(SignType.TIME_OUT.getCode());
            signInLog.setSignOutTime(now);
            signInLogMapper.updateByPrimaryKey(signInLog);
            return false;
        }
        // 重新登录
        signInLog.setType(SignType.UPDATE.getCode());
        signInLog.setSignOutTime(now);
        signInLogMapper.updateByPrimaryKey(signInLog);
        SignInLog signIn = new SignInLog()
                .withType(signInLog.getType())
                .withToken(signInLog.getToken())
                .withIp(signInLog.getIp())
                .withSource(signInLog.getSource());
        signInLogMapper.insert(signIn);
        return true;
    }

    @Override
    public AccountInfo getAccountInfo(String token) {
        String aid = redisTemplate.boundValueOps(TOKEN_PREFIX + token).get();
        return getAccount(aid).setToken(token);
    }

    @Override
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
    public List<AccountInfo> findAllAccount() {
        return accountMapper
                .selectByExample(new AccountExample())
                .stream()
                .map(AccountInfo::withAccount)
                .peek(accountInfo -> accountInfo.setOrganizations(getOrganization(accountInfo.getSignId())))
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
    public AccountInfo getAccount(String id) {
        return Optional.ofNullable(accountMapper.selectByPrimaryKey(id))
                .map(AccountInfo::withAccount)
                .map(account -> account
                        .setOrganizations(getOrganization(id)))
                .orElse(null);
    }


    private List<AccountOrganizationInfo> getOrganization(String aid) {
        AccountOrganizationExample example = new AccountOrganizationExample();
        example.createCriteria()
                .andAidEqualTo(aid);
        return accountOrganizationMapper
                .selectByExample(example)
                .stream()
                .map(AccountOrganizationInfo::createByAccountOrganization)
                .peek(organization -> organization.withOrganization(organizationMapper.selectByPrimaryKey(organization.getOid())))
                .collect(Collectors.toList());
    }

}
