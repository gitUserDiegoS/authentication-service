package co.com.pragma.model.user.excepcion;

public class EmailInvalidException extends RuntimeException {
    public EmailInvalidException(String message) {
        super(message);
    }
}
