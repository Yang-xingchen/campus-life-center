package campuslifecenter.usercenter.model;

public enum SignInType {
    /**
     * 2xx: 成功
     */
    SUCCESS(200, true, "成功"),

    REPEAT(201, true, "重复登录"),

    ALREADY_SIGN_IN(202, true, "已经登录"),
    /**
     * 3xx: 账户错误
     */
    ACCOUNT_NOT_EXIST(301, false, "账户名或密码错误"),

    PASSWORD_ERROR(302, false, "账户名或密码错误"),
    /**
     * 4xx: 其它错误
     */
    UNKNOWN(400, false, "未知错误"),

    UNKNOWN_COOKIE(401, false, "未知登录id"),

    TEST_SIGN_IN_TOO_MUCH(402, false, "尝试次数过多");

    public int code;
    public boolean success;
    public String message;

    SignInType(int code, boolean success, String message) {
        this.code = code;
        this.success = success;
        this.message = message;
    }
}
