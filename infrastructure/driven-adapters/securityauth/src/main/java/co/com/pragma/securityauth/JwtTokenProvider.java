package co.com.pragma.securityauth;

import co.com.pragma.model.tokenprovider.ItokenProvider;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.exception.NotValidTokenException;
import co.com.pragma.model.user.role.RoleEnum;
import co.com.pragma.model.usersession.UserSession;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;


import lombok.extern.java.Log;
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
public class JwtTokenProvider implements ItokenProvider {


    private final SecretKey secret;
    private final long expiration;

    public JwtTokenProvider(@Value("${JWT_SECRET}") String jwtSecret,
                            @Value("${JWT_EXPIRATION}") long expiration) {

        log.info("secret " + jwtSecret);

        this.secret = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;

    }


    @Override
    public String generateToken(User user) {
        log.info("como llega user get mail" + user.getEmail());
        log.info("como llega user get idrole" + user.getIdRole());

        log.info("como llega user get mail" + RoleEnum.fromId(user.getIdRole()).getName());

        return Jwts.builder()
                .setSubject(user.getIdDocument())
                .claim("email", user.getEmail())
                .claim("role", RoleEnum.fromId(user.getIdRole()).getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secret)
                .compact();

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
                    claims.get("email", String.class),
                    claims.get("role", String.class)
            );

            log.info("como llega user get claims rol " + session.getName());
            return Mono.just(session);
        } catch (JwtException e) {
            return Mono.error(new NotValidTokenException(INVALID_TOKEN));
        }
    }
}

