package co.com.pragma.api;

import co.com.pragma.api.dto.CreateUserDto;
import co.com.pragma.api.mapper.UserMapperDto;
import co.com.pragma.usecase.user.IuserUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Validated
public class Handler {

    private static final Logger log = LoggerFactory.getLogger(Handler.class);

    private final IuserUseCase userUseCase;

    private final UserMapperDto userMapperDto;

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


}
