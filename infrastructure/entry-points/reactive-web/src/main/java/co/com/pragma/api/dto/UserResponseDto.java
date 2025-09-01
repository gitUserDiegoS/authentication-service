package co.com.pragma.api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response for a user created")
public class UserResponseDto {

    @Schema(description = "idUser for an user created", example = "21")
    private Long idUser;

}
