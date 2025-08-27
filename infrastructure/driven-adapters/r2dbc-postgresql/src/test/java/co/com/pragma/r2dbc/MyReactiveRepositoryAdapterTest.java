package co.com.pragma.r2dbc;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.valueObjects.Email;
import co.com.pragma.model.user.valueObjects.LastName;
import co.com.pragma.model.user.valueObjects.Name;
import co.com.pragma.model.user.valueObjects.SalaryBase;
import co.com.pragma.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyReactiveRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    MyReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    MyReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Mock
    TransactionalOperator operator;

    private final Clock fixedClock = Clock.fixed(
            LocalDate.of(2025, 8, 27).atStartOfDay(ZoneId.systemDefault()).toInstant(),
            ZoneId.systemDefault()
    );


    private final UserEntity userEntity = UserEntity.builder()
            .idUser(1L)
            .idDocument("1234567")
            .name("Name")
            .lastname("lastName")
            .birthdate(LocalDate.now(fixedClock))
            .address("address")
            .mobile("3199689469")
            .email("email@email.com")
            .salaryBase(BigDecimal.valueOf(1))
            .idRole(1L)
            .build();


    private final User user = User.builder()
            .idUser(1L)
            .idDocument("1234567")
            .name(new Name("name"))
            .lastname(new LastName("lastName"))
            .birthdate(LocalDate.now(fixedClock))
            .address("address")
            .mobile("3199689469")
            .email(new Email("email@email.com"))
            .salaryBase(new SalaryBase(BigDecimal.valueOf(1)))
            .idRole(1L)
            .build();

    @Test
    void mustFindByEmail() {
        when(mapper.map(userEntity, User.class)).thenReturn(user);
        when(repository.findByEmail(any(String.class))).thenReturn(Mono.just(userEntity));

        Mono<User> result = repositoryAdapter.findByEmail("email@email.com");

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getIdUser().equals(1L))
                .verifyComplete();
    }


    @Test
    void mustSaveValue() {

        when(mapper.map(userEntity, User.class)).thenReturn(user);
        when(mapper.map(user, UserEntity.class)).thenReturn(userEntity);
        when(operator.transactional(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(repository.save(any(UserEntity.class))).thenReturn(Mono.just(userEntity));

        Mono<User> result = repositoryAdapter.save(user);

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();
    }
}
