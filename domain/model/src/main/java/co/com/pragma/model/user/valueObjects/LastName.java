package co.com.pragma.model.user.valueObjects;

import co.com.pragma.model.user.excepcion.LastNameInvalidException;
import co.com.pragma.model.user.excepcion.NameInvalidException;

public class LastName {

    private final String value;

    public LastName(String value) {

        if (value == null || value.isBlank()) {
            throw new LastNameInvalidException("LastName must not be null or blank");
        }
        this.value = value.trim();
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
