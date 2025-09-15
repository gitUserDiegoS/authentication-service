package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IuserUseCase {

    Mono<User> saveUser(User user);

    Mono<User> getUserByEmail(String email);

    Mono<User> findByIdDocument(String idDocument);

    Flux<User> getUsersByEmailBatch(Flux<String> emails);


}
