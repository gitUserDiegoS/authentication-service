package co.com.pragma.securityauth.exception;

import co.com.pragma.model.baseexception.BusinessException;
import co.com.pragma.model.user.constants.ErrorCodes;

public class NotValidTokenException extends BusinessException {
    public NotValidTokenException(String message) {
        super(message, ErrorCodes.UNAUTHORIZED);
    }
}
