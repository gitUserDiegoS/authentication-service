package co.com.pragma.securityauth;

import co.com.pragma.model.tokenprovider.TokenProvider;
import co.com.pragma.model.tokenprovider.gateways.TokenProviderRepository;
import co.com.pragma.model.user.User;
import co.com.pragma.securityauth.exception.NotValidTokenException;
import co.com.pragma.model.user.roleenum.RoleEnum;
import co.com.pragma.model.usersession.UserSession;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static co.com.pragma.model.user.constants.ModelExceptionMessages.INVALID_TOKEN;

@Slf4j
@Component
public class JwtTokenProvider implements TokenProviderRepository {

    public static final String EMAIL = "email";
    public static final String ROLE = "role";
    public static final String TYPE = "Bearer";

    private final SecretKey secret;
    private final long expiration;

    public JwtTokenProvider(@Value("${JWT_SECRET}") String jwtSecret,
                            @Value("${JWT_EXPIRATION}") long expiration) {

        this.secret = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;

    }

    @Override
    public Mono<TokenProvider> generateToken(User user) {

        return Mono.just(TokenProvider.builder()
                        .token(Jwts.builder()
                                .setSubject(user.getIdDocument())
                                .claim(EMAIL, user.getEmail())
                                .claim(ROLE, RoleEnum.fromId(user.getIdRole()).getName())
                                .setIssuedAt(new Date())
                                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                                .signWith(secret)
                                .compact())
                        .type(TYPE)
                        .expires(expiration)
                        .build())
                .doOnNext(generated -> log.info("Token Generated successfully for userName {}", user.getEmail()))
                .doOnError(error -> log.error("Error in generateToken method, failed with message: {}", error.getMessage()));


    }

    @Override
    public Mono<UserSession> validateToken(String token) {

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token).getBody();


            UserSession session = new UserSession(
                    Long.valueOf(claims.getSubject()),
                    claims.get(EMAIL, String.class),
                    claims.get(ROLE, String.class)
            );

            return Mono.just(session)
                    .doOnNext(generated -> log.info("Token validated successfully"))
                    .doOnError(error -> log.error("Error in validateToken method, failed with message: {}", error.getMessage()));

        } catch (JwtException e) {
            return Mono.error(new NotValidTokenException(INVALID_TOKEN));
        }
    }
}

