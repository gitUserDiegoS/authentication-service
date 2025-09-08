package co.com.pragma.usecase.auth;


import co.com.pragma.model.tokenprovider.TokenProvider;
import reactor.core.publisher.Mono;

public interface IauthUseCase {

    Mono<TokenProvider> login(String email, String password);

}
