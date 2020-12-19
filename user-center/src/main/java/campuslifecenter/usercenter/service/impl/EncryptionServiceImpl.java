package campuslifecenter.usercenter.service.impl;

import campuslifecenter.usercenter.entry.Account;
import campuslifecenter.usercenter.entry.SecurityLogKey;
import campuslifecenter.usercenter.mapper.AccountMapper;
import campuslifecenter.usercenter.mapper.SecurityLogMapper;
import campuslifecenter.usercenter.service.EncryptionService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    private final Cipher PRI_CIPHER;
    private final Cipher PUB_CIPHER;

    private AccountMapper accountMapper;
    private SecurityLogMapper securityLogMapper;
    private RedisTemplate<String, String> redisTemplate;

    private final String KEY_PREFIX;
    private final int KEY_EXPIRE;
    private final TimeUnit KEY_EXPIRE_UNIT;

    private final String PUBLIC_KEY;
    private String algorithm;
    private int keySize;

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Autowired
    public EncryptionServiceImpl(@Value("${user-center.security.normal-algorithm}") String normalAlgorithm,
                                 @Value("${user-center.security.normal-public-key}") String pubKey,
                                 @Value("${user-center.security.normal-private-key}") String priKey,
                                 @Value("${user-center.security.security-algorithm}") String algorithm,
                                 @Value("${user-center.security.security-algorithm-key-size}") int size,
                                 @Value("${user-center.security.redis.prefix}") String keyPrefix,
                                 @Value("${user-center.security.redis.expire}") int keyExpire,
                                 @Value("${user-center.security.redis.expire-unit}") String keyExpireUnit,
                                 RedisTemplate<String, String> redisTemplate,
                                 AccountMapper accountMapper,
                                 SecurityLogMapper securityLogMapper) throws Throwable {
        this.KEY_PREFIX = keyPrefix;
        this.KEY_EXPIRE = keyExpire;
        this.KEY_EXPIRE_UNIT = TimeUnit.valueOf(keyExpireUnit.toUpperCase());

        this.PUBLIC_KEY = pubKey;
        this.algorithm = algorithm;
        this.keySize = size;
        this.redisTemplate = redisTemplate;
        this.accountMapper = accountMapper;
        this.securityLogMapper = securityLogMapper;

        PublicKey publicKey = KeyFactory.getInstance(normalAlgorithm)
                .generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(pubKey)));
        PUB_CIPHER = Cipher.getInstance(normalAlgorithm);
        PUB_CIPHER.init(Cipher.ENCRYPT_MODE, publicKey);

        PrivateKey privateKey = KeyFactory.getInstance(normalAlgorithm)
                .generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(priKey)));
        PRI_CIPHER = Cipher.getInstance(normalAlgorithm);
        PRI_CIPHER.init(Cipher.DECRYPT_MODE, privateKey);
    }

    @Override
    public String getPublecKey() {
        return PUBLIC_KEY;
    }

    @Override
    public String rsaEncode(String message) {
        try {
            return Base64.encodeBase64String(PUB_CIPHER.doFinal(message.getBytes(UTF_8)));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String rsaDecode(String code) {
        try {
            return new String(PRI_CIPHER.doFinal(Base64.decodeBase64(code)), UTF_8);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String encode(String id, String message) {
        BoundValueOperations<String, String> valueOps = redisTemplate.boundValueOps(KEY_PREFIX + id);
        try {
            SecretKeySpec keySpec = getKey(
                    Objects.requireNonNull(valueOps.get(), "Not start security").getBytes());
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return Base64.encodeBase64String(cipher.doFinal(message.getBytes(UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decode(String id, String code) {
        BoundValueOperations<String, String> valueOps = redisTemplate.boundValueOps(KEY_PREFIX + id);
        try {
            SecretKeySpec keySpec = getKey(
                    Objects.requireNonNull(valueOps.get(), "Not start security").getBytes());
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return new String(cipher.doFinal(Base64.decodeBase64(code)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SecretKeySpec getKey(byte[] key) throws Exception{
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key);
        keyGenerator.init(keySize, secureRandom);
        return new SecretKeySpec(keyGenerator.generateKey().getEncoded(), algorithm);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean startSecurity(String aid, String securityPwd, String key) {
        securityPwd = rsaDecode(securityPwd);
        key = rsaEncode(key);
        Account account = accountMapper.selectByPrimaryKey(aid);
        if (account == null || !PASSWORD_ENCODER.matches(account.getSecurityKey(), securityPwd)) {
            return false;
        }
        SecurityLogKey securityLog = new SecurityLogKey()
                .withAid(aid)
                .withInputTime(new Date());
        securityLogMapper.insert(securityLog);
        BoundValueOperations<String, String> valueOps = redisTemplate.boundValueOps(KEY_PREFIX + aid);
        valueOps.set(key, KEY_EXPIRE, KEY_EXPIRE_UNIT);
        return true;
    }

    @Override
    public boolean exitSecurity(String aid) {
        redisTemplate.delete(KEY_PREFIX + aid);
        return true;
    }
}
