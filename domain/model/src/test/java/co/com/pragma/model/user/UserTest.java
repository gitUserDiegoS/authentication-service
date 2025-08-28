package co.com.pragma.model.user;

import co.com.pragma.model.user.valueObjects.Email;
import co.com.pragma.model.user.valueObjects.LastName;
import co.com.pragma.model.user.valueObjects.Name;
import co.com.pragma.model.user.valueObjects.SalaryBase;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    void shouldBuildUserCorrectly() {
        User user = User.builder()
                .idUser(1L)
                .idDocument("1234567")
                .name(new Name("Diego"))
                .lastname(new LastName("Ramirez"))
                .birthdate(LocalDate.of(1990, 5, 20))
                .address("Street 123")
                .mobile("3199999999")
                .email(new Email("diego@email.com"))
                .salaryBase(new SalaryBase(BigDecimal.valueOf(2000)))
                .idRole(2L)
                .build();

        assertEquals("Diego", user.getName().getValue());
        assertEquals("Ramirez", user.getLastname().getValue());
        assertEquals("diego@email.com", user.getEmail().getValue());
    }
}