package campuslifecenter.common.exception;

import java.util.Objects;

public class AuthException extends ResponseException{

    public static final int AUTH_FAIL_CODE = 403;

    public AuthException() {
        super("auth fail", AUTH_FAIL_CODE);
    }

    public AuthException(String message) {
        super(message, AUTH_FAIL_CODE);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause, AUTH_FAIL_CODE);
    }

    public AuthException(Throwable cause) {
        super(cause, AUTH_FAIL_CODE);
    }

    public AuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, AUTH_FAIL_CODE);
    }

    public static void checkThrow(String aid1, String aid2) {
        if (!Objects.equals(aid1, aid2)) {
            throw new AuthException();
        }
    }
}
