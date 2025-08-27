package co.com.pragma.model.user.valueObjects;

import co.com.pragma.model.user.excepcion.NameInvalidException;
import co.com.pragma.model.user.excepcion.SalaryBaseInvalidException;

import java.math.BigDecimal;


public class SalaryBase {

    private final BigDecimal value;

    public SalaryBase(BigDecimal value) {

        if (value.compareTo((BigDecimal.ZERO)) < 0
                || value.compareTo((BigDecimal.valueOf(15000000))) > 0
        )
        {
            throw new SalaryBaseInvalidException("Salary base must be in a range between 0 and 15000000");
        }

        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
