package co.com.pragma.usecase.user;

import co.com.pragma.model.passwordencoder.gateways.PasswordEncoderRepository;
import co.com.pragma.model.user.User;

import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.model.user.constants.ModelExceptionMessages;
import co.com.pragma.model.user.constants.ValidationConstants;
import co.com.pragma.usecase.constants.UseCaseExceptionMessages;
import co.com.pragma.model.user.exception.EmailAlreadyRegisteredException;
import co.com.pragma.model.user.exception.EmailInvalidException;
import co.com.pragma.model.user.exception.NameInvalidException;
import co.com.pragma.model.user.exception.SalaryBaseInvalidException;
import co.com.pragma.model.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase implements IuserUseCase {

    private final UserRepository userRepository;

    private final PasswordEncoderRepository passwordEncoder;


    @Override
    public Mono<User> saveUser(User user) {


        return validateUserByEmail(user)
                .flatMap(this::validateUserByName)
                .flatMap(this::validateUserBySalaryBase)
                .flatMap(validUser -> getUserByEmail(validUser.getEmail())
                        .flatMap(userExists ->
                                Mono.<User>error(new EmailAlreadyRegisteredException(String.format(UseCaseExceptionMessages.EMAIL_REGISTERED, userExists.getEmail())
                                ))
                        )
                        .switchIfEmpty(
                                passwordEncoder.encode(validUser.getPassword())
                                        .flatMap(encodedPass -> {
                                            validUser.setPassword(encodedPass);
                                            return userRepository.save(validUser);
                                        })

                        )

                );
    }

    @Override
    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Mono<User> findByIdDocument(String idDocument) {
        return userRepository.findByIdDocument(idDocument)
                .switchIfEmpty(Mono.<User>error(new UserNotFoundException(String.format(UseCaseExceptionMessages.USER_NOT_FOUND_EXCEPTION, idDocument))));
    }

    public Mono<User> validateUserByEmail(User user) {

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return Mono.error(new EmailInvalidException(ModelExceptionMessages.INVALID_EMAIL));
        }

        if (!user.getEmail().matches(ValidationConstants.EMAIL_REGEX)) {
            return Mono.error(new EmailInvalidException(String.format(ModelExceptionMessages.INVALID_FORMAT_EMAIL, user.getEmail())));
        }


        return Mono.just(user);
    }


    public Mono<User> validateUserByName(User user) {


        if (user.getName() == null || user.getName().isBlank()) {
            return Mono.error(new NameInvalidException(ModelExceptionMessages.INVALID_NAME));
        }

        if (user.getLastname() == null || user.getLastname().isBlank()) {
            return Mono.error(new NameInvalidException(ModelExceptionMessages.INVALID_LAST_NAME));
        }

        return Mono.just(user);
    }

    public Mono<User> validateUserBySalaryBase(User user) {

        if (user.getSalaryBase() == null) {
            return Mono.error(new SalaryBaseInvalidException(ModelExceptionMessages.INVALID_SALARY));
        }

        if (user.getSalaryBase().compareTo((ValidationConstants.MIN_SALARY_RANGE)) < 0
                || user.getSalaryBase().compareTo((ValidationConstants.MAX_SALARY_RANGE)) > 0) {
            return Mono.error(new SalaryBaseInvalidException(String.format(ModelExceptionMessages.INVALID_SALARY_RANGE, user.getSalaryBase())));
        }

        return Mono.just(user);
    }


}
