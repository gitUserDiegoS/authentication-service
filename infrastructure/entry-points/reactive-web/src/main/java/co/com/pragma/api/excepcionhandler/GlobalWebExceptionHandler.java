package co.com.pragma.api.excepcionhandler;

import co.com.pragma.api.dto.ErrorResponseDto;

import co.com.pragma.model.user.excepcion.EmailInvalidException;
import co.com.pragma.model.user.excepcion.LastNameInvalidException;
import co.com.pragma.model.user.excepcion.NameInvalidException;
import co.com.pragma.model.user.excepcion.SalaryBaseInvalidException;
import co.com.pragma.usecase.exception.EmailAlreadyRegisteredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalWebExceptionHandler {

    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleEmailRegistered(
            EmailAlreadyRegisteredException ex, ServerWebExchange exchange) {

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "EMAIL_ALREADY_REGISTERED",
                ex.getMessage(),
                exchange.getRequest().getPath().value()
        );

        return Mono.just(org.springframework.http.ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse));
    }

    @ExceptionHandler(Exception.class)
    public Mono<org.springframework.http.ResponseEntity<ErrorResponseDto>> handleGenericException(
            Exception ex, ServerWebExchange exchange) {

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "INTERNAL_ERROR",
                ex.getMessage(),
                exchange.getRequest().getPath().value()
        );

        return Mono.just(org.springframework.http.ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse));
    }


    @ExceptionHandler(DuplicateKeyException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleDuplicateKey(
            DuplicateKeyException ex, ServerWebExchange exchange) {

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "EMAIL_ALREADY_REGISTERED",
                ex.getMessage(),
                exchange.getRequest().getPath().value()
        );

        return Mono.just(org.springframework.http.ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse));
    }

    @ExceptionHandler(NameInvalidException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleNameInvalidException(
            NameInvalidException ex, ServerWebExchange exchange) {

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "INVALID_NAME",
                ex.getMessage(),
                exchange.getRequest().getPath().value()
        );

        return Mono.just(org.springframework.http.ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse));
    }

    @ExceptionHandler(EmailInvalidException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleEmailInvalidException(
            EmailInvalidException ex, ServerWebExchange exchange) {

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "INVALID_EMAIL",
                ex.getMessage(),
                exchange.getRequest().getPath().value()
        );

        return Mono.just(org.springframework.http.ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse));
    }

    @ExceptionHandler(LastNameInvalidException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleLastNameInvalidException(
            LastNameInvalidException ex, ServerWebExchange exchange) {

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "INVALID_LAST_NAME",
                ex.getMessage(),
                exchange.getRequest().getPath().value()
        );

        return Mono.just(org.springframework.http.ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse));
    }

    @ExceptionHandler(SalaryBaseInvalidException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleSalaryBaseInvalidException(
            SalaryBaseInvalidException ex, ServerWebExchange exchange) {

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                "INVALID_SALARY_BASE",
                ex.getMessage(),
                exchange.getRequest().getPath().value()
        );

        return Mono.just(org.springframework.http.ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse));
    }

}



