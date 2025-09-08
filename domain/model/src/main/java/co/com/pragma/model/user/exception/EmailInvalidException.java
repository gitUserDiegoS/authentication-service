package co.com.pragma.model.user.exception;

import co.com.pragma.model.user.constants.ErrorCodes;

public class EmailInvalidException extends ValidationException {
    public EmailInvalidException(String message) {
        super(message, ErrorCodes.BAD_REQUEST);
    }

    public EmailInvalidException(String message, String code) {
        super(message, code);
    }
}
