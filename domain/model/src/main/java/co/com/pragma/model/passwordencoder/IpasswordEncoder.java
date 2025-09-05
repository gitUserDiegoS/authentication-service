package co.com.pragma.model.passwordencoder;

import reactor.core.publisher.Mono;

public interface IpasswordEncoder {

    Mono<String> encode(String rawPassword);

    Mono<Boolean> matches(String rawPassword, String encodedPassword);

}
