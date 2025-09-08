package co.com.pragma.securityauth;

import co.com.pragma.model.passwordencoder.gateways.PasswordEncoderRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Getter
@Setter
@Component
public class BCryptPasswordEncoderAdapter implements PasswordEncoderRepository {

    private final BCryptPasswordEncoder delegate;

    public BCryptPasswordEncoderAdapter() {
        this.delegate = new BCryptPasswordEncoder();
    }

    @Override
    public Mono<String> encode(String rawPassword) {
        return Mono.defer(() ->
                        Mono.fromCallable(() -> delegate.encode(rawPassword))
                                .subscribeOn(Schedulers.boundedElastic())
                )
                .doOnNext(logger -> log.info("password encoded successfully"))
                .doOnError(error -> log.error("Error in encode method, failed with message: {}", error.getMessage()));
    }

    @Override
    public Mono<Boolean> matches(String rawPassword, String encodedPassword) {
        return Mono.defer(() ->
                        Mono.fromCallable(() -> delegate.matches(rawPassword, encodedPassword))
                                .subscribeOn(Schedulers.boundedElastic())
                )
                .doOnNext(logger -> log.info("User matches login successfully"))
                .doOnError(error -> log.error("Error in matches method, failed with message: {}", error.getMessage()));

    }
}
