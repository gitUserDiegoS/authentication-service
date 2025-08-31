package co.com.pragma.model.user.constants;

import java.math.BigDecimal;

public final class ValidationConstants {

    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    public static final BigDecimal MIN_SALARY_RANGE = BigDecimal.ZERO;

    public static final BigDecimal MAX_SALARY_RANGE = BigDecimal.valueOf(15000000);

    private ValidationConstants() {
        throw new IllegalStateException("Utility class");
    }

}
