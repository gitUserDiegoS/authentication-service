package co.com.pragma.api.dto;

import lombok.*;

@AllArgsConstructor
@Getter
public class ErrorResponseDto {

    private String code;
    private String message;
    private String Path;
}
