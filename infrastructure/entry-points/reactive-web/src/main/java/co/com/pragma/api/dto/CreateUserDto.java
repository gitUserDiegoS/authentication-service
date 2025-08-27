package co.com.pragma.api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {


    private String idDocument;

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Lastname cannot be null")
    @NotBlank(message = "Lastname cannot be empty")
    private String lastname;

    private LocalDate birthdate;

    private String address;

    private String mobile;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should have a valid format")
    private String email;

    @Min(value = 0, message = "Salary base must be greater than 0")
    @Max(value = 15000000, message = "Salary base must be maximum 15000000")
    private BigDecimal salaryBase;
    private Long idRole;
}
