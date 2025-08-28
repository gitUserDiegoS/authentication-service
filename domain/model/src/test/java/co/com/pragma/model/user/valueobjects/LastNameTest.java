package co.com.pragma.model.user.valueobjects;

import ch.qos.logback.core.util.StringUtil;
import co.com.pragma.model.user.excepcion.LastNameInvalidException;
import co.com.pragma.model.user.excepcion.NameInvalidException;
import co.com.pragma.model.user.valueObjects.Email;
import co.com.pragma.model.user.valueObjects.LastName;
import co.com.pragma.model.user.valueObjects.Name;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LastNameTest {
    @Test
    void shuldValidateWhenLastNameIsEmpty() {
        assertThrows(LastNameInvalidException.class,
                () -> new LastName(StringUtil.nullStringToEmpty(null)));

    }

    @Test
    void shoulContinueWhenLastNameIsValid() {
        LastName lastName = new LastName("lastName");
        assertEquals("lastName", lastName.getValue());
    }
}
