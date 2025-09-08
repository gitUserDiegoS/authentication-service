package co.com.pragma.usecase.auth;

import co.com.pragma.model.passwordencoder.gateways.PasswordEncoderRepository;
import co.com.pragma.model.tokenprovider.TokenProvider;
import co.com.pragma.model.tokenprovider.exception.CredentialsException;
import co.com.pragma.model.tokenprovider.gateways.TokenProviderRepository;
import co.com.pragma.model.user.User;

import co.com.pragma.model.user.gateways.UserRepository;

import co.com.pragma.usecase.constants.UseCaseExceptionMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AuthUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoderRepository passwordEncoder;

    @Mock
    private TokenProviderRepository tokenProviderRepository;

    private IauthUseCase authUseCase;


    private final Clock fixedClock = Clock.fixed(
            LocalDate.of(2025, 8, 27).atStartOfDay(ZoneId.systemDefault()).toInstant(),
            ZoneId.systemDefault()
    );

    private final User user = User.builder()
            .idUser(1L)
            .idDocument("1234567")
            .name("name")
            .lastname("lastName")
            .birthdate(LocalDate.now(fixedClock))
            .address("address")
            .mobile("3199689469")
            .email("email@email.com")
            .salaryBase(BigDecimal.valueOf(1))
            .idRole(1L)
            .password("p@s7word")
            .build();

    private final TokenProvider tokenProvided = TokenProvider.builder()
            .token("$2a$10$WORYuN8CojVxmzofh.4S9.7dHnSf2703Vusn5/BdTOl37d/7rpJry")
            .type("Bearer")
            .expires(3600L)
            .build();

    @BeforeEach
    void setUp() {
        authUseCase = new AuthUseCase(userRepository, passwordEncoder, tokenProviderRepository);
    }

    @Test
    void shouldLoginSuccessful() {
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.just(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(Mono.just(true));
        when(tokenProviderRepository.generateToken(any(User.class))).thenReturn(Mono.just(tokenProvided));

        StepVerifier.create(authUseCase.login("email@email.com", "p@s7word"))
                .expectNext(tokenProvided)
                .verifyComplete();

    }

    @Test
    void shouldFailLoginWhenNotUserNameFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());


        StepVerifier.create(authUseCase.login("email@email.com", "p@s7word"))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(CredentialsException.class);
                    assertThat(error.getMessage()).isEqualTo(String.format(UseCaseExceptionMessages.INVALID_CREDENTIAL_EXCEPTION));
                })
                .verify();

    }

    @Test
    void shouldFailLoginWhenPasswordNotMatches() {
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.just(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(Mono.just(false));

        StepVerifier.create(authUseCase.login("email@email.com", "p@s7word"))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(CredentialsException.class);
                    assertThat(error.getMessage()).isEqualTo(String.format(UseCaseExceptionMessages.INVALID_CREDENTIAL_EXCEPTION));
                })
                .verify();

    }

}
