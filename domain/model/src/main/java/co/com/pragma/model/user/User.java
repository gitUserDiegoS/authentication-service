package co.com.pragma.model.user;

import co.com.pragma.model.user.valueObjects.Email;
import co.com.pragma.model.user.valueObjects.LastName;
import co.com.pragma.model.user.valueObjects.Name;
import co.com.pragma.model.user.valueObjects.SalaryBase;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Long idUser;
    private String idDocument;
    private Name name;
    private LastName lastname;
    private LocalDate birthdate;
    private String address;
    private String mobile;
    private Email email;
    private SalaryBase salaryBase;
    private Long idRole;

}