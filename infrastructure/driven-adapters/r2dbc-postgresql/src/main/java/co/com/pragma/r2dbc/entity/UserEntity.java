package co.com.pragma.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("usuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {

    @Id
    @Column("id_usuario")
    private Long idUser;

    @Column("documento_identidad")
    private String idDocument;

    @Column("nombre")
    private String name;

    @Column("apellido")
    private String lastname;

    @Column("fecha_nacimiento")
    private LocalDate birthdate;

    @Column("direccion")
    private String address;

    @Column("telefono")
    private String mobile;

    private String email;

    @Column("salario_base")
    private BigDecimal salaryBase;

    @Column("id_rol")
    private Long idRole;

    private String password;


}
