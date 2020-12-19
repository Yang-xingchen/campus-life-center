package campuslifecenter.usercenter.service;

public interface EncryptionService {

    String getPublecKey();

    String rsaEncode(String message);

    String rsaDecode(String code);

    String encode(String id, String message);

    String decode(String id, String code);

    /**
     * 进入安全模式
     * @param aid 账户id
     * @param securityPwd 账户密码
     * @param key 秘钥
     */
    boolean startSecurity(String aid, String securityPwd, String key);

    /**
     * 退出安全模式
     * @param aid 账户id
     */
    boolean exitSecurity(String aid);
}
