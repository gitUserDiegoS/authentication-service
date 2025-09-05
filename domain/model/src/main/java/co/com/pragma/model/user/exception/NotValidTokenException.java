package co.com.pragma.model.user.exception;

import co.com.pragma.model.user.constants.ErrorCodes;

public class NotValidTokenException extends BusinessException {
    public NotValidTokenException(String message) {
        super(message, ErrorCodes.UNAUTHORIZED);
    }
}
