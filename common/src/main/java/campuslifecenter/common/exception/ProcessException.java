package campuslifecenter.common.exception;

import campuslifecenter.common.model.Response;

public class ProcessException extends ResponseException{

    public static final String USER_CENTER = "3";
    public static final String NOTICE = "4";
    public static final String TODO = "5";
    public static final String INFO = "6";

    public ProcessException(String module, String message, Response<?> response) {
        super(message + ": " + response.getMessage(), Integer.parseInt(module + response.getCode()));
    }

    public static void check(String module, String message, Response<?> response) {
        if (!response.isSuccess()) {
            throw new ProcessException(module, message, response);
        }
    }

}
