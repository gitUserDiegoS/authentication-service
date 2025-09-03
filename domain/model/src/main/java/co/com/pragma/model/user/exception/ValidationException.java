package co.com.pragma.model.user.exception;

public abstract class ValidationException extends RuntimeException {

    private final String errorCode;

    protected ValidationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
