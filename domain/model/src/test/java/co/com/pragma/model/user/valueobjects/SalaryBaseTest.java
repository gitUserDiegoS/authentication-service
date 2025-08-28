package co.com.pragma.model.user.valueobjects;

import ch.qos.logback.core.util.StringUtil;
import co.com.pragma.model.user.excepcion.NameInvalidException;
import co.com.pragma.model.user.excepcion.SalaryBaseInvalidException;
import co.com.pragma.model.user.valueObjects.LastName;
import co.com.pragma.model.user.valueObjects.Name;
import co.com.pragma.model.user.valueObjects.SalaryBase;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SalaryBaseTest {

    @Test
    void shuldValidateSalaryIsLessThanMin() {
        assertThrows(SalaryBaseInvalidException.class,
                () -> new SalaryBase(BigDecimal.valueOf(-1)));

    }

    @Test
    void shouldValidateSalaryGreatherThaMmax() {
        assertThrows(SalaryBaseInvalidException.class,
                () -> new SalaryBase(BigDecimal.valueOf(18000000)));
    }

    @Test
    void shoulContinueWhenSalaryIsValid() {
        SalaryBase salary = new SalaryBase(BigDecimal.valueOf(13000000));
        assertEquals(new BigDecimal(13000000), salary.getValue());
    }
}
