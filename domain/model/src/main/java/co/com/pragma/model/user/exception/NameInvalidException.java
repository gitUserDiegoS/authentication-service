package co.com.pragma.model.user.exception;

import co.com.pragma.model.user.constants.ErrorCodes;

public class NameInvalidException extends ValidationException {
    public NameInvalidException(String message) {
        super(message, ErrorCodes.BAD_REQUEST);
    }
}
