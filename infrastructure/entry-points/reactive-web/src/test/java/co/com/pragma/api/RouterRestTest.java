package co.com.pragma.api;

import co.com.pragma.api.dto.CreateUserDto;
import co.com.pragma.api.dto.UserResponseDto;
import co.com.pragma.api.mapper.UserMapperDto;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.valueObjects.Email;
import co.com.pragma.model.user.valueObjects.LastName;
import co.com.pragma.model.user.valueObjects.Name;
import co.com.pragma.model.user.valueObjects.SalaryBase;
import co.com.pragma.usecase.user.IuserUseCase;
import co.com.pragma.usecase.user.UserUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.mockito.ArgumentMatchers.any;

@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private IuserUseCase userUseCase;

    @MockitoBean
    private UserMapperDto userMapperDto;

    private final String users = "/api/v1/usuarios";

    private final Clock fixedClock = Clock.fixed(
            LocalDate.of(2025, 8, 27).atStartOfDay(ZoneId.systemDefault()).toInstant(),
            ZoneId.systemDefault()
    );

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

    private final CreateUserDto createUserDto = new CreateUserDto("1234567",
            "Name",
            "lastName",
            LocalDate.now(fixedClock),
            "address",
            "3199689469",
            "email@email.com",
            BigDecimal.valueOf(1),
            1L);

    private final UserResponseDto responseDto = new UserResponseDto(user.getIdUser());

    @BeforeEach
    void setup() {
        Mockito.when(userMapperDto.toModel(any(CreateUserDto.class))).thenReturn(user);
        Mockito.when(userUseCase.saveUser(any(User.class))).thenReturn(Mono.just(user));
        Mockito.when(userMapperDto.toResponse(any(User.class))).thenReturn(responseDto);
    }

    @Test
    void testListenPOSTUseCase() {


        webTestClient.post()
                .uri(users)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(createUserDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse.getIdUser()).isEqualTo(user.getIdUser());
                        }
                );
    }
}
