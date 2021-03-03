package campuslifecenter.usercenter.service.impl;

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
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    private final Cipher PRI_CIPHER;
    private final Cipher PUB_CIPHER;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${user-center.security.redis.prefix}")
    private String KEY_PREFIX;
    @Value("${user-center.security.redis.expire}")
    private int KEY_EXPIRE;
    private TimeUnit KEY_EXPIRE_UNIT;

    private String PUBLIC_KEY;
    @Value("${user-center.security.security-algorithm}")
    private String algorithm;
    private String algorithmInstance = "AES/ECB/PKCS5Padding";

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Autowired
    public EncryptionServiceImpl(@Value("${user-center.security.normal-algorithm}") String normalAlgorithm,
                                 @Value("${user-center.security.normal-public-key}") String pubKey,
                                 @Value("${user-center.security.normal-private-key}") String priKey,
                                 @Value("${user-center.security.redis.expire-unit}") String keyExpireUnit) throws Throwable {
        this.KEY_EXPIRE_UNIT = TimeUnit.valueOf(keyExpireUnit.toUpperCase());

        this.PUBLIC_KEY = pubKey;

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
    public String getPublicKey() {
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
    public String encode(String token, String message) {
        BoundValueOperations<String, String> valueOps = redisTemplate.boundValueOps(KEY_PREFIX + token);
        try {
            SecretKeySpec keySpec = getKey(
                    Objects.requireNonNull(valueOps.get(), "Not start security").getBytes());
            Cipher cipher = Cipher.getInstance(algorithmInstance);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return Base64.encodeBase64String(cipher.doFinal(message.getBytes(UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decode(String token, String code) {
        BoundValueOperations<String, String> valueOps = redisTemplate.boundValueOps(KEY_PREFIX + token);
        try {
            SecretKeySpec keySpec = getKey(
                    Objects.requireNonNull(valueOps.get(), "Not start security").getBytes());
            Cipher cipher = Cipher.getInstance(algorithmInstance);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return new String(cipher.doFinal(Base64.decodeBase64(code)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private SecretKeySpec getKey(byte[] key) throws Exception{
        return new SecretKeySpec(key, algorithm);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean startSecurity(String token, String key) {
        key = rsaDecode(key);
        BoundValueOperations<String, String> valueOps = redisTemplate.boundValueOps(KEY_PREFIX + token);
        valueOps.set(key, KEY_EXPIRE, KEY_EXPIRE_UNIT);
        return true;
    }

    @Override
    public boolean exitSecurity(String token) {
        return Objects.equals(redisTemplate.delete(KEY_PREFIX + token),  true);
    }
}
