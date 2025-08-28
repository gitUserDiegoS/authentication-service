package co.com.pragma.model.user.valueobjects;

import ch.qos.logback.core.util.StringUtil;
import co.com.pragma.model.user.excepcion.EmailInvalidException;
import co.com.pragma.model.user.excepcion.NameInvalidException;
import co.com.pragma.model.user.valueObjects.Email;
import co.com.pragma.model.user.valueObjects.Name;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NameTest {

    @Test
    void shuldValidateWhenNameIsEmpty() {
        assertThrows(NameInvalidException.class,
                () -> new Name(StringUtil.nullStringToEmpty(null)));

    }

    @Test
    void shoulContinueWhenNameIsValid() {
        Name name = new Name("name");
        assertEquals("name", name.getValue());
    }
}
