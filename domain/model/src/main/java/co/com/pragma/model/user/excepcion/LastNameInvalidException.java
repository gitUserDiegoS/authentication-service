package co.com.pragma.model.user.excepcion;

public class LastNameInvalidException extends RuntimeException {
    public LastNameInvalidException(String message) {
        super(message);
    }
}

