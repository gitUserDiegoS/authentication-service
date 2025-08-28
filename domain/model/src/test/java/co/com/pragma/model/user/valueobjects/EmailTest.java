package co.com.pragma.model.user.valueobjects;

import ch.qos.logback.core.util.StringUtil;
import co.com.pragma.model.user.excepcion.EmailInvalidException;
import co.com.pragma.model.user.valueObjects.Email;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

public class EmailTest {

    @Test
    void shouldAcceptWhenValidEmail() {
        Email email = new Email("acceptableformat@correo.com");
        assertEquals("acceptableformat@correo.com", email.getValue());
    }

    @Test
    void shouldFailWithInvalidEmail() {
        assertThrows(EmailInvalidException.class,
                () -> new Email("not-an-email"));
    }

    @Test
    void shouldFailWithInvalidBlank() {
        assertThrows(EmailInvalidException.class,
                () -> new Email(StringUtil.nullStringToEmpty(null)));
    }


}
