package campuslifecenter.usercenter.model;

public enum SignInType {
    /**
     *
     */
    SUCCESS(true),
    /**
     *
     */
    REPEAT(true),

    ALREADY_SIGN_IN(true),
    /**
     *
     */
    ACCOUNT_NOT_EXIST(false),
    /**
     *
     */
    PASSWORD_ERROR(false),
    /**
     *
     */
    UNKNOWN(false),

    UNKNOWN_COOKIE(false),

    TEST_SIGN_IN_TOO_MUCH(false);

    public boolean success;

    SignInType(boolean success) {
        this.success = success;
    }
}
