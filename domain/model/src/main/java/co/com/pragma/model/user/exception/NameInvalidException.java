package co.com.pragma.model.user.exception;

public class NameInvalidException extends RuntimeException {
    public NameInvalidException(String message) {
        super(message);
    }
}
