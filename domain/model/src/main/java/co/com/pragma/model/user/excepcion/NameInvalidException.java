package co.com.pragma.model.user.excepcion;

public class NameInvalidException extends RuntimeException {
    public NameInvalidException(String message) {
        super(message);
    }
}
