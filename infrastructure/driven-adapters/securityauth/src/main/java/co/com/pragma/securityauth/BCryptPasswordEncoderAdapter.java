package co.com.pragma.securityauth;

import co.com.pragma.model.passwordencoder.IpasswordEncoder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Getter
@Setter
@Component
public class BCryptPasswordEncoderAdapter implements IpasswordEncoder {

    private String secret;

    private long expiration;

    private final BCryptPasswordEncoder delegate;

    public BCryptPasswordEncoderAdapter() {
        this.delegate = new BCryptPasswordEncoder();
    }

    @Override
    public Mono<String> encode(String rawPassword) {

        log.info("Diego encode metodo, raw password " + rawPassword);
        String encode = delegate.encode(rawPassword);
        log.info("Diego encode metodo, delegate.encode " + encode);

        return Mono.fromCallable(() -> delegate.encode(rawPassword));
        //return delegate.encode(rawPassword);

    }

    @Override
    public Mono<Boolean> matches(String rawPassword, String encodedPassword) {

        String encode = delegate.encode(rawPassword);
        log.info("Diego encodea, raw password " + encode);

        log.info("Diego matches metodo, raw password " + rawPassword);
        log.info("Diego matches metodo, raw encodedPassword " + encodedPassword);

        boolean matches = delegate.matches(rawPassword, encodedPassword);
        log.info("Diego matches devuelve metodo, delegate.encode " + matches);

        return Mono.fromCallable(() -> delegate.matches(rawPassword, encodedPassword));

    }
}
