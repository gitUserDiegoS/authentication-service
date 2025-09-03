package co.com.pragma.model.user.exception;

import co.com.pragma.model.user.constants.ErrorCodes;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String message) {
        super(message, ErrorCodes.NOT_FOUND);
    }
}
