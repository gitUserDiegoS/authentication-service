package co.com.pragma.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response with the user found by email")
public class UserFoundResponseDto {

    @Schema(description = "User idDocument", example = "1234567")
    private String idDocument;

    @Schema(description = "User email", example = "correo@gmail.com.co")
    private String email;

    //validar si crear otro response
    @Schema(description = "User name")
    private String name;

    @Schema(description = "User lastname")
    private String lastname;

    @Schema(description = "User salaryBase")
    private BigDecimal salaryBase;


}
