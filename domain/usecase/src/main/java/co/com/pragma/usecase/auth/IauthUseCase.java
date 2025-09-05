package co.com.pragma.usecase.auth;


import reactor.core.publisher.Mono;

public interface IauthUseCase {

    Mono<String> login(String email, String password);

}
