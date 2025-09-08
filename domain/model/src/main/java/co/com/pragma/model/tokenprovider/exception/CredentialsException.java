package co.com.pragma.model.tokenprovider.exception;

import co.com.pragma.model.baseexception.BusinessException;
import co.com.pragma.model.user.constants.ErrorCodes;

public class CredentialsException extends BusinessException {
    public CredentialsException(String message) {
        super(message, ErrorCodes.UNAUTHORIZED);
    }
}
