package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.usecase.exception.EmailAlreadyRegisteredException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    public Mono<User> saveUser(User user) {

        return getUserByEmail(user.getEmail().toString())
                .flatMap(userIncoming -> Mono.<User>error(new EmailAlreadyRegisteredException(
                        "Email " + userIncoming.getEmail() + " is already registered"

                )))
                .switchIfEmpty(userRepository.save(user));
    }

    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
