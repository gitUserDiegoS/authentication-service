package co.com.pragma.model.user.valueObjects;


import co.com.pragma.model.user.excepcion.NameInvalidException;


public class Name {
    private final String value;

    public Name(String value) {

        if (value == null || value.isBlank()) {
            throw new NameInvalidException("Name must not be null or blank");
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
