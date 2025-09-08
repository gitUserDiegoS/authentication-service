package co.com.pragma.usecase.user;


import co.com.pragma.model.passwordencoder.gateways.PasswordEncoderRepository;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.constants.ModelExceptionMessages;
import co.com.pragma.model.user.exception.*;
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
class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoderRepository passwordEncoder;

    private IuserUseCase userUseCase;


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

    @BeforeEach
    void setUp() {

        userUseCase = new UserUseCase(userRepository, passwordEncoder);
    }

    @Test
    void shouldSaveEmailNotRegistered() {
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));
        when(passwordEncoder.encode(anyString())).thenReturn(Mono.just("$2a$10$WORYuN8CojVxmzofh.4S9.7dHnSf2703Vusn5/BdTOl37d/7rpJry"));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void shouldFailWhenEmailAlreadyRegistered() {
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.just(user));
        when(passwordEncoder.encode(anyString())).thenReturn(Mono.just("$2a$10$WORYuN8CojVxmzofh.4S9.7dHnSf2703Vusn5/BdTOl37d/7rpJry"));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(EmailAlreadyRegisteredException.class);
                    assertThat(UseCaseExceptionMessages.EMAIL_REGISTERED).isEqualTo("Email %s registered before");
                })
                .verify();
    }

    @Test
    void shouldFailWhenEmailEmpty() {

        user.setEmail("");

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(EmailInvalidException.class);
                    assertThat(error.getMessage()).isEqualTo(String.format(ModelExceptionMessages.INVALID_EMAIL));
                })
                .verify();
    }

    @Test
    void shouldFailWhenEmailNull() {

        user.setEmail(null);

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(EmailInvalidException.class);
                    assertThat(error.getMessage()).isEqualTo(String.format(ModelExceptionMessages.INVALID_EMAIL));
                })
                .verify();
    }

    @Test
    void shouldFailWhenEmailInvalidFormat() {

        user.setEmail("not_valid");

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(EmailInvalidException.class);
                    assertThat(error.getMessage()).isEqualTo(String.format(ModelExceptionMessages.INVALID_FORMAT_EMAIL, "not_valid"));
                })
                .verify();
    }

    @Test
    void shouldFailWhenNameEmpty() {

        user.setName("");

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(NameInvalidException.class);
                    assertThat(error.getMessage()).isEqualTo(String.format(ModelExceptionMessages.INVALID_NAME));
                })
                .verify();
    }

    @Test
    void shouldFailWhenNameNull() {

        user.setName(null);

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(NameInvalidException.class);
                    assertThat(error.getMessage()).isEqualTo(String.format(ModelExceptionMessages.INVALID_NAME));
                })
                .verify();
    }

    @Test
    void shouldFailWhenLastNameEmpty() {

        user.setLastname("");

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(NameInvalidException.class);
                    assertThat(error.getMessage()).isEqualTo(String.format(ModelExceptionMessages.INVALID_LAST_NAME));
                })
                .verify();
    }

    @Test
    void shouldFailWhenLastNameNull() {

        user.setLastname(null);

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(NameInvalidException.class);
                    assertThat(error.getMessage()).isEqualTo(String.format(ModelExceptionMessages.INVALID_LAST_NAME));
                })
                .verify();
    }

    @Test
    void shouldFailWhenSalaryBaseNull() {

        user.setSalaryBase(null);

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(SalaryBaseInvalidException.class);
                    assertThat(error.getMessage()).isEqualTo(String.format(ModelExceptionMessages.INVALID_SALARY));
                })
                .verify();
    }

    @Test
    void shouldFailWhenSalaryBaseOutMinRange() {

        user.setSalaryBase(new BigDecimal(-1));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(SalaryBaseInvalidException.class);
                    assertThat(error.getMessage()).isEqualTo(String.format(ModelExceptionMessages.INVALID_SALARY_RANGE, user.getSalaryBase()));
                })
                .verify();
    }

    @Test
    void shouldFailWhenSalaryBaseOutMaxRange() {

        user.setSalaryBase(new BigDecimal(18000000));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(SalaryBaseInvalidException.class);
                    assertThat(error.getMessage()).isEqualTo(String.format(ModelExceptionMessages.INVALID_SALARY_RANGE, user.getSalaryBase()));
                })
                .verify();
    }


    @Test
    void shouldGetUserByIdDocument() {
        when(userRepository.findByIdDocument(anyString())).thenReturn(Mono.just(user));

        StepVerifier.create(userUseCase.findByIdDocument(user.getIdDocument()))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void shouldFailWhenUserNotFoundByIdDocument() {
        when(userRepository.findByIdDocument(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(userUseCase.findByIdDocument(user.getIdDocument()))
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(UserNotFoundException.class);
                    assertThat(error.getMessage()).isEqualTo(String.format(UseCaseExceptionMessages.USER_NOT_FOUND_EXCEPTION, user.getIdDocument()));
                })
                .verify();
    }


}
