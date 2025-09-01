package co.com.pragma.usecase.constants;

public final class UseCaseExceptionMessages {

    public static final String EMAIL_REGISTERED = "Email %s registered before";

    private UseCaseExceptionMessages() {
        throw new IllegalStateException("Utility class");
    }

}
