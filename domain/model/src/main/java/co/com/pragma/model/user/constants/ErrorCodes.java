package co.com.pragma.model.user.constants;

public final class ErrorCodes {

    public static final String NOT_FOUND = "NOT_FOUND";

    public static final String CONFLICT = "CONFLICT";

    public static final String BAD_REQUEST = "BAD_REQUEST";

    private ErrorCodes() {
        throw new IllegalStateException("Utility class");
    }

}
