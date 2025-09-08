package co.com.pragma.api;

import co.com.pragma.api.dto.CreateUserDto;
import co.com.pragma.api.dto.LoginRequestDto;

import co.com.pragma.api.mapper.LoginMapperDto;
import co.com.pragma.api.mapper.UserMapperDto;
import co.com.pragma.usecase.auth.IauthUseCase;
import co.com.pragma.usecase.user.IuserUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Validated
public class Handler {

    private static final Logger log = LoggerFactory.getLogger(Handler.class);

    private final IuserUseCase userUseCase;

    private final IauthUseCase authUseCase;

    private final UserMapperDto userMapperDto;

    private final LoginMapperDto loginMapperDto;


    @PreAuthorize("hasAnyRole('ADMIN','ADVISOR')")
    public Mono<ServerResponse> listenCreateUserUseCase(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(CreateUserDto.class)
                .doOnNext(user -> log.trace("Begin request to create user with email: {}", user.getEmail()))
                .map(userMapperDto::toModel)
                .flatMap(userUseCase::saveUser)
                .map(userMapperDto::toResponse)
                .flatMap(saved -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(saved))
                .doOnError(err -> log.error("Error in handler{}", err.getMessage(), err));

    }


    public Mono<ServerResponse> listenGetUserByDocumentId(ServerRequest serverRequest) {
        String idDocument = serverRequest.pathVariable("idDocument");

        return userUseCase.findByIdDocument(idDocument)
                .doOnNext(user -> log.trace("Begin request to get user by email: {}", user.getEmail()))
                .map(userMapperDto::toFoundResponse)
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnError(err -> log.error("Error in handler-->listenGetUserByEmail{}", err.getMessage(), err));

    }


    public Mono<ServerResponse> listenLoginUser(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(LoginRequestDto.class)
                .doOnNext(user -> log.trace("Init login for user: {}", user.getEmail()))
                .flatMap(login -> authUseCase.login(login.getEmail(), login.getPassword()))
                .map(loginMapperDto::toResponse)
                .flatMap(token -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(token))
                .doOnError(err -> log.error("Error in handler-->listenLoginUser{}", err.getMessage(), err));

    }


    public Mono<ServerResponse> listenUsersByEmail(ServerRequest serverRequest) {


        return serverRequest.bodyToMono(new ParameterizedTypeReference<List<String>>() {
                })
                .flatMapMany(userUseCase::getUsersByEmailBatch)
                .map(userMapperDto::toFoundResponse)
                .collectList()
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user));

        //.switchIfEmpty(ServerResponse.notFound().build())
        //.doOnError(err -> log.error("Error in handler-->listenGetUserByEmail{}", err.getMessage(), err));

    }
}

