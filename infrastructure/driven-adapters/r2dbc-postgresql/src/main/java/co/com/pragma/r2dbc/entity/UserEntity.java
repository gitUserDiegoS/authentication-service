package co.com.pragma.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {

    @Id
    @Column("id_user")
    private Long idUser;

    @Column("id_document")
    private String idDocument;

    private String name;

    private String lastname;

    private LocalDate birthdate;

    private String address;

    private String mobile;

    private String email;

    @Column("salary_base")
    private BigDecimal salaryBase;

    @Column("id_role")
    private Long idRole;

}
