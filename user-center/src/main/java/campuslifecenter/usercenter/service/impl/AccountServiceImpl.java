package campuslifecenter.usercenter.service.impl;

import campuslifecenter.usercenter.entry.*;
import campuslifecenter.usercenter.mapper.*;
import campuslifecenter.usercenter.model.AccountInfo;
import campuslifecenter.usercenter.model.SignInType;
import campuslifecenter.usercenter.model.SignType;
import campuslifecenter.usercenter.service.AccountService;
import campuslifecenter.usercenter.service.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static campuslifecenter.usercenter.model.SignInType.*;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private SignInLogMapper signInLogMapper;
    @Autowired
    private SecurityLogMapper securityLogMapper;
    @Autowired
    private AccountOrganizationMapper accountOrganizationMapper;
    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public static final int SIGN_IN_COUNT = 3;

    public static final String UUID_PREFIX = "SIGN_IN_UUID_";
    public static final String COOKIE_PREFIX = "COOKIE_";

    public AccountServiceImpl(AccountMapper accountMapper,
                              SignInLogMapper signInLogMapper,
                              SecurityLogMapper securityLogMapper,
                              AccountOrganizationMapper accountOrganizationMapper,
                              OrganizationMapper organizationMapper,
                              RedisTemplate<String, String> redisTemplate) {
        this.accountMapper = accountMapper;
        this.signInLogMapper = signInLogMapper;
        this.securityLogMapper = securityLogMapper;
        this.accountOrganizationMapper = accountOrganizationMapper;
        this.organizationMapper = organizationMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String signInId() {
        String uuid = UUID.randomUUID().toString();
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(UUID_PREFIX + uuid,
                Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        redisAtomicInteger.set(0);
        redisAtomicInteger.expire(1, HOURS);
        return uuid;
    }

    @Override
    public SignInType signIn(String aid, String pwd, SignInLog sign) {
        pwd = encryptionService.rsaDecode(pwd);

        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(UUID_PREFIX + sign.getCookie(),
                Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        // 违法cookie
        try {
            redisAtomicInteger.get();
        } catch (DataRetrievalFailureException e) {
            return UNKNOWN_COOKIE;

        }
        // 尝试登录次数过多
        if (redisAtomicInteger.incrementAndGet() > SIGN_IN_COUNT) {
            return TEST_SIGN_IN_TOO_MUCH;
        }
        // 重复登录
        BoundValueOperations<String, String> cookieValueOps = redisTemplate.boundValueOps(COOKIE_PREFIX + sign.getCookie());
        if (!Objects.equals(cookieValueOps.setIfAbsent("", 1, MINUTES), true)) {
            if ("".equals(cookieValueOps.get())) {
                return REPEAT;
            } else {
                return ALREADY_SIGN_IN;
            }
        }
        // 账户不存在
        Account account = accountMapper.selectByPrimaryKey(aid);
        if (account == null) {
            redisTemplate.delete(COOKIE_PREFIX + sign.getCookie());
            return ACCOUNT_NOT_EXIST;
        }
        // 密码错误
        if (!PASSWORD_ENCODER.matches(pwd, account.getPassword())) {
            redisTemplate.delete(COOKIE_PREFIX + sign.getCookie());
            return PASSWORD_ERROR;
        }
        // 下线已登录
        SignInLogExample example = new SignInLogExample();
        example.createCriteria()
                .andAidEqualTo(aid)
                .andSignOutTimeIsNull();
        List<SignInLog> signeds = signInLogMapper.selectByExample(example);
        if (signeds.size() != 0) {
            signeds.forEach(signed -> {
                signed.setType(SignType.SIGN_IN.getCode());
                signed.setSignOutTime(sign.getSignInTime());
                signInLogMapper.updateByPrimaryKey(signed);
                redisTemplate.delete(COOKIE_PREFIX + signed.getCookie());
            });
        }
        // 登录
        int count = signInLogMapper.insert(sign);
        redisTemplate.delete(UUID_PREFIX + sign.getCookie());
        if (count != 1) {
            return UNKNOWN;
        }
        cookieValueOps.set(aid);
        return SUCCESS;
    }

    @Override
    public boolean signOut(String aid) {
        Date now = new Date();
        SignInLogExample example = new SignInLogExample();
        example.createCriteria()
                .andAidEqualTo(aid)
                .andSignOutTimeIsNull();
        List<SignInLog> signeds = signInLogMapper.selectByExample(example);
        if (signeds.size() != 0) {
            signeds.forEach(signed -> {
                signed.setType(SignType.SIGN_OUT.getCode());
                signed.setSignOutTime(now);
                signInLogMapper.updateByPrimaryKey(signed);
                redisTemplate.delete(COOKIE_PREFIX + signed.getCookie());
            });
        }
        return true;
    }

    @Override
    public boolean checkSignInId(String uuid) {
        if (Objects.equals(redisTemplate.hasKey(COOKIE_PREFIX + uuid), true)) {
            return true;
        }
        SignInLogExample example = new SignInLogExample();
        example.createCriteria()
                .andCookieEqualTo(uuid)
                .andSignOutTimeIsNull();
        return signInLogMapper.countByExample(example) == 1;
    }

    @Override
    public boolean startSecurity(String aid, String securityPwd, String key) {
        Account account = accountMapper.selectByPrimaryKey(aid);
        if (account == null) {
            return false;
        }
        if (!PASSWORD_ENCODER.matches(account.getSecurityKey(), securityPwd)) {
            return false;
        }
        SecurityLogKey securityLog = new SecurityLogKey()
                .withAid(aid)
                .withInputTime(new Date());
        securityLogMapper.insert(securityLog);
        encryptionService.setKey(aid, key);
        return true;
    }

    @Override
    public boolean exitSecurity(String aid) {
        encryptionService.delKey(aid);
        return true;
    }

    @Override
    public AccountInfo getAccountInfo(String cookie) {
        String aid = redisTemplate.boundValueOps(COOKIE_PREFIX + cookie).get();
        Account account = accountMapper.selectByPrimaryKey(aid);
        if (account == null) {
            return null;
        }
        AccountInfo accountInfo = new AccountInfo()
                .setSignId(aid)
                .setName(account.getName())
                .setGender(account.getGender())
                .setCreateData(account.getCreateData())
                .setCookie(cookie);
        AccountOrganizationExample example = new AccountOrganizationExample();
        example.createCriteria()
                .andAidEqualTo(aid);
        List<Organization> organizations = accountOrganizationMapper
                .selectByExample(example)
                .stream()
                .map(accountOrganization -> organizationMapper.selectByPrimaryKey(accountOrganization.getOid()))
                .collect(Collectors.toList());
        accountInfo.setOrganizations(organizations);
        return accountInfo;
    }

}
