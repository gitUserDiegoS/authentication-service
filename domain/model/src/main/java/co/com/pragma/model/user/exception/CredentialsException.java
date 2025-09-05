package co.com.pragma.model.user.exception;

import co.com.pragma.model.user.constants.ErrorCodes;

public class CredentialsException extends BusinessException {
    public CredentialsException(String message) {
        super(message, ErrorCodes.UNAUTHORIZED);
    }
}
