import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class GetInfo {
    @Test
    public void getPassword() {
        String password = "user";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode(password));
    }

    @Test
    public void getRsaKey() throws Throwable{
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024, new SecureRandom());
        KeyPair keyPair = generator.generateKeyPair();
        String pub = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
        String pri = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
        System.out.println("pub: " + pub);
        System.out.println("pri: " + pri);

        String message = "text";
        PublicKey publicKey = KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(pub)));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        String code = Base64.encodeBase64String(cipher.doFinal(message.getBytes(StandardCharsets.UTF_8)));

        PrivateKey privateKey = KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(Base64.decodeBase64(pri)));
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        String m = new String(cipher.doFinal(Base64.decodeBase64(code)), StandardCharsets.UTF_8);
        Assertions.assertEquals(m, message);
    }
}
