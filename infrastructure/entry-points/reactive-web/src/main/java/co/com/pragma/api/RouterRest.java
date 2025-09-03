package co.com.pragma.api;

import co.com.pragma.api.config.UserPath;
import co.com.pragma.api.dto.CreateUserDto;
import co.com.pragma.api.dto.UserResponseDto;
import co.com.pragma.api.excepcionhandler.GlobalWebExceptionHandler;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
@Tag(name = "Users", description = "User operations")
public class RouterRest {

    private final UserPath userPath;
    private final Handler userHandler;

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = Handler.class,
                    beanMethod = "listenCreateUserUseCase",
                    operation = @Operation(
                            operationId = "createUser",
                            summary = "Create user with data related",
                            requestBody = @RequestBody
                                    (description = "CreateUserDto", required = true,
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = CreateUserDto.class)
                                            )
                                    ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "User created successfully",
                                            content = @Content(
                                                    schema = @Schema(implementation =
                                                            UserResponseDto.class))),
                                    @ApiResponse(responseCode = "400", description = "Bad request due to validation result",
                                            content = @Content(
                                                    schema = @Schema(implementation =
                                                            GlobalWebExceptionHandler.class))),
                                    @ApiResponse(responseCode = "500", description = "Internal error",
                                            content = @Content(
                                                    schema = @Schema(implementation =
                                                            GlobalWebExceptionHandler.class)))
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/v1/usuarios/{id}",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.GET,
                    beanClass = Handler.class,
                    beanMethod = "listenGetUserByEmail",
                    operation = @Operation(
                            operationId = "getUserByEmail",
                            summary = "Get User By Email",
                            requestBody = @RequestBody
                                    (description = "CreateUserDto", required = true,
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    schema = @Schema(implementation = CreateUserDto.class)
                                            )
                                    ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "User created successfully",
                                            content = @Content(
                                                    schema = @Schema(implementation =
                                                            UserResponseDto.class))),
                                    @ApiResponse(responseCode = "400", description = "Bad request due to validation result",
                                            content = @Content(
                                                    schema = @Schema(implementation =
                                                            GlobalWebExceptionHandler.class))),
                                    @ApiResponse(responseCode = "500", description = "Internal error",
                                            content = @Content(
                                                    schema = @Schema(implementation =
                                                            GlobalWebExceptionHandler.class)))
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST(userPath.getUsers()), userHandler::listenCreateUserUseCase)
                .andRoute(GET(userPath.getUsersByDocumentId()), userHandler::listenGetUserByDocumentId);


    }
}
