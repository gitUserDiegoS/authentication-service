package co.com.pragma.model.user.exception;

import co.com.pragma.model.user.constants.ErrorCodes;

public class EmailAlreadyRegisteredException extends BusinessException {
    public EmailAlreadyRegisteredException(String message) {
        super(message, ErrorCodes.CONFLICT);
    }
}
