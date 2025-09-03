package co.com.pragma.usecase.constants;

public final class UseCaseExceptionMessages {

    public static final String EMAIL_REGISTERED = "Email %s registered before";

    public static final String USER_NOT_FOUND_EXCEPTION = "User with id %s not found";


    private UseCaseExceptionMessages() {
        throw new IllegalStateException("Utility class");
    }

}
