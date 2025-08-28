package co.com.pragma.usecase.user;


import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.model.user.valueObjects.*;
import co.com.pragma.usecase.exception.EmailAlreadyRegisteredException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    private UserUseCase userUseCase;

    private final User user = User.builder()
            .idUser(1L)
            .idDocument("1234567")
            .name(new Name("Diego"))
            .lastname(new LastName("Ramirez"))
            .birthdate(LocalDate.of(1990, 5, 20))
            .address("Street 123")
            .mobile("3199999999")
            .email(new Email("diego@email.com"))
            .salaryBase(new SalaryBase(BigDecimal.valueOf(2000)))
            .idRole(2L)
            .build();

    @BeforeEach
    void setUp() {
        userUseCase = new UserUseCase(userRepository);
    }

    @Test
    void shouldSaveEmailNotRegistered() {
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void shouldFailWhenEmailAlreadyRegistered() {
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.just(user));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        StepVerifier.create(userUseCase.saveUser(user))
                .expectError(EmailAlreadyRegisteredException.class)
                .verify();
    }


}
