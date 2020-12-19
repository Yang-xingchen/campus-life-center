package campuslifecenter.usercenter.model;

import java.util.Arrays;

public enum SignType {
    /**
     * 手动退出
     */
    SIGN_OUT(0),
    /**
     * 超时退出
     */
    TIME_OUT(1),
    /**
     * 重新登录
     */
    SIGN_IN(2),
    /**
     * 刷新登录状态
     */
    UPDATE(3);

    private final byte code;

    SignType(int code) {
        this.code = (byte) code;
    }

    public byte getCode() {
        return code;
    }

    public static SignType getByCode(byte code) {
        return Arrays.stream(SignType.values())
                .filter(signType -> signType.code == code)
                .findAny()
                .orElse(null);
    }
}
