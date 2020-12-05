package campuslifecenter.usercenter.service;

public interface EncryptionService {

    String rsaEncode(String message);

    String rsaDecode(String code);

    void setKey(String id, String key);

    void delKey(String id);

    String encode(String id, String message);

    String decode(String id, String code);

}
