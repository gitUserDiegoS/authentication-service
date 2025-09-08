package co.com.pragma.usecase.auth;

import co.com.pragma.model.passwordencoder.gateways.PasswordEncoderRepository;

import co.com.pragma.model.tokenprovider.TokenProvider;
import co.com.pragma.model.tokenprovider.gateways.TokenProviderRepository;
import co.com.pragma.model.tokenprovider.exception.CredentialsException;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.usecase.constants.UseCaseExceptionMessages;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class AuthUseCase implements IauthUseCase {

    private final UserRepository userRepository;

    private final PasswordEncoderRepository passwordEncoder;

    private final TokenProviderRepository tokenProviderRepository;

    @Override
    public Mono<TokenProvider> login(String email, String password) {

        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new CredentialsException(UseCaseExceptionMessages.INVALID_CREDENTIAL_EXCEPTION)))
                .flatMap(user ->
                        passwordEncoder.matches(password, user.getPassword())
                                .flatMap(match -> {
                                    if (!Boolean.TRUE.equals(match)) {
                                        return Mono.<TokenProvider>error(new CredentialsException(UseCaseExceptionMessages.INVALID_CREDENTIAL_EXCEPTION));
                                    }
                                    return tokenProviderRepository.generateToken(user);
                                })
                );
    }
}
