package co.com.pragma.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request to create a new user")
public class CreateUserDto {

    @Schema(description = "User idDocument", example = "1234567")
    private String idDocument;

    @Schema(description = "User name", example = "Jhon")
    private String name;

    @Schema(description = "User lastname", example = "Dhoe")
    private String lastname;

    @Schema(description = "User birthdate", example = "1989-01-02")
    private LocalDate birthdate;

    @Schema(description = "User address", example = "calle 88")
    private String address;

    @Schema(description = "User mobile", example = "3197899685")
    private String mobile;

    @Email(message = "Email should have a valid format")
    @Schema(description = "User email", example = "correo@gmail.com.co")
    private String email;

    @Schema(description = "User salaryBase", example = "12000000")
    private BigDecimal salaryBase;

    @Schema(description = "User idDocument", example = "1")
    private Long idRole;
}
