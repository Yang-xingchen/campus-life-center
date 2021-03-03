package campuslifecenter.usercenter.service;

public interface EncryptionService {

    String getPublicKey();

    String rsaEncode(String message);

    String rsaDecode(String code);

    String encode(String token, String message);

    String decode(String token, String code);

    /**
     * 进入安全模式
     * @param aid 账户id
     * @param securityPwd 账户密码
     * @param key 秘钥
     */
    boolean startSecurity(String token, String key);

    /**
     * 退出安全模式
     * @param aid 账户id
     */
    boolean exitSecurity(String token);
}
