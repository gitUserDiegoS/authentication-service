package co.com.pragma.model.tokenprovider.gateways;

import co.com.pragma.model.tokenprovider.TokenProvider;
import co.com.pragma.model.user.User;
import co.com.pragma.model.usersession.UserSession;
import reactor.core.publisher.Mono;

public interface TokenProviderRepository {

    Mono<TokenProvider> generateToken(User user);

    Mono<UserSession> validateToken(String token);
}
