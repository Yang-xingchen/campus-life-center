package campuslifecenter.usercenter.service.impl;

import campuslifecenter.usercenter.service.EncryptionService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.concurrent.TimeUnit.MINUTES;

@Service
public class EncryptionServiceImpl implements EncryptionService {

    private final String PRI_KEY;
    private final String PUB_KEY;
    private final Cipher PRI_CIPHER;
    private final Cipher PUB_CIPHER;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public static final String KEY_PREFIX = "KEY_";
    private String algorithm;
    private int keySize;

    public EncryptionServiceImpl(@Value("${private-key}") String priKey,
                                 @Value("${public-key}") String pubKey,
                                 @Value("${algorithm}") String algorithm,
                                 @Value("${algorithm-key-size}") int size,
                                 RedisTemplate<String, String> redisTemplate) throws Throwable {
        this.PRI_KEY = priKey;
        this.PUB_KEY = pubKey;
        this.algorithm = algorithm;
        this.keySize = size;
        this.redisTemplate = redisTemplate;

        PublicKey publicKey = KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(pubKey)));
        PUB_CIPHER = Cipher.getInstance("RSA");
        PUB_CIPHER.init(Cipher.ENCRYPT_MODE, publicKey);

        PrivateKey privateKey = KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(priKey)));
        PRI_CIPHER = Cipher.getInstance("RSA");
        PRI_CIPHER.init(Cipher.DECRYPT_MODE, privateKey);
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
    public void setKey(String id, String key) {
        BoundValueOperations<String, String> valueOps = redisTemplate.boundValueOps(KEY_PREFIX + id);
        valueOps.set(key, 15, MINUTES);
    }

    @Override
    public void delKey(String id) {
        redisTemplate.delete(KEY_PREFIX + id);
    }

    @Override
    public String encode(String id, String message) {
        BoundValueOperations<String, String> valueOps = redisTemplate.boundValueOps(KEY_PREFIX + id);
        try {
            SecretKeySpec keySpec = getKey(
                    Objects.requireNonNull(valueOps.get(), "key is null").getBytes());
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
                    Objects.requireNonNull(valueOps.get(), "key is null").getBytes());
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
}
