package co.com.pragma.model.user.exception;

import co.com.pragma.model.user.constants.ErrorCodes;

public class SalaryBaseInvalidException extends ValidationException {
    public SalaryBaseInvalidException(String message) {
        super(message, ErrorCodes.BAD_REQUEST);
    }
}
