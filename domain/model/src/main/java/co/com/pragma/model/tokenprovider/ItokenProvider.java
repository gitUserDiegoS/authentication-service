package co.com.pragma.model.tokenprovider;

import co.com.pragma.model.user.User;
import co.com.pragma.model.usersession.UserSession;
import reactor.core.publisher.Mono;

public interface ItokenProvider {

    String generateToken(User user);

    Mono<UserSession> validateToken(String token);
}
