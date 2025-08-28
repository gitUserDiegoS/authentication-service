package co.com.pragma.model.user.valueObjects;


import co.com.pragma.model.user.excepcion.EmailInvalidException;

public class Email {

    private final String value;

    public Email(String value) {


        if (value == null || !value.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new EmailInvalidException("Invalid format email: " + value);
        }
        this.value = value.toLowerCase();
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

}