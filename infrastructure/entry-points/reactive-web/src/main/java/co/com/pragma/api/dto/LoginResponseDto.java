package co.com.pragma.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response for login successful")
public class LoginResponseDto {

    @Schema(description = "Token security, used for use services after login")
    String token;

    @Schema(description = "Token type")
    String type;

    @Schema(description = "Token time expiration in ms")
    Long expires;
}
