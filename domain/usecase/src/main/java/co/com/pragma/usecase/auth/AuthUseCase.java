package co.com.pragma.usecase.auth;

import co.com.pragma.model.passwordencoder.IpasswordEncoder;
import co.com.pragma.model.passwordencoder.PasswordEncoder;
import co.com.pragma.model.tokenprovider.ItokenProvider;
import co.com.pragma.model.tokenprovider.TokenProvider;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.exception.CredentialsException;
import co.com.pragma.model.user.exception.UserNotFoundException;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static co.com.pragma.model.user.constants.ModelExceptionMessages.INVALID_CREDENTIALS;


@RequiredArgsConstructor
public class AuthUseCase implements IauthUseCase {

    private final UserRepository userRepository;

    private final IpasswordEncoder passwordEncoder;

    private final ItokenProvider tokenProvider;

    @Override
    public Mono<String> login(String email, String password) {

        System.out.println("login auth usecase password");

        return userRepository.findByEmail(email)
                .doOnNext(user -> System.out.println("user --use case" + user.getPassword()))
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")))
                .flatMap(user ->
                        passwordEncoder.matches(password, user.getPassword())
                                .flatMap(match -> {
                                    if (!match) {
                                        return Mono.error(new CredentialsException("Invalid credentials"));
                                    }
                                    return Mono.just(tokenProvider.generateToken(user));
                                })
                );

    }
}
