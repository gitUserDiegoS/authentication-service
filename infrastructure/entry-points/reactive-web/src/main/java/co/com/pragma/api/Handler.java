package co.com.pragma.api;

import co.com.pragma.model.user.User;
import co.com.pragma.usecase.user.UserUseCase;
import jakarta.validation.Valid;
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

    private final UserUseCase userUseCase;


    public Mono<ServerResponse> listenCreateUserUseCase(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(User.class)
                .doOnNext(user -> log.trace("Begin request to create user"))
                .flatMap(userUseCase::saveUser)
                .doOnNext(savedUser -> log.trace("User created successfully"))
                .flatMap(savedUser -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedUser))
                .doOnError(err -> log.error("Error in handler: {}", err.getMessage(), err));
    }
}
