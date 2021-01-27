package campuslifecenter.common.exception;

public class ResponseException extends RuntimeException{

    private int code;
    public static final int COMMON = 200;

    public ResponseException() {
        code = COMMON;
    }

    public ResponseException(String message) {
        super(message);
        code = COMMON;
    }

    public ResponseException(String message, Throwable cause) {
        super(message, cause);
        code = COMMON;
    }

    public ResponseException(Throwable cause) {
        super(cause);
        code = COMMON;
    }

    public ResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        code = COMMON;
    }

    public ResponseException(int code) {
        this.code = code;
    }

    public ResponseException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ResponseException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public ResponseException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public ResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public ResponseException setCode(int code) {
        this.code = code;
        return this;
    }
}
