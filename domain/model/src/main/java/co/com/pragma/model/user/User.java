package co.com.pragma.model.user;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Long idUser;
    private String idDocument;
    private String name;
    private String lastname;
    private LocalDate birthdate;
    private String address;
    private String mobile;
    private String email;
    private BigDecimal salaryBase;
    private Long idRole;
    private String password;

}