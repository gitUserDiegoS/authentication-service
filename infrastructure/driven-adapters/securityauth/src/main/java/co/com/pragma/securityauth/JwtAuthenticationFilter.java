package co.com.pragma.securityauth;

import co.com.pragma.model.tokenprovider.gateways.TokenProviderRepository;
import co.com.pragma.model.user.constants.ModelExceptionMessages;
import co.com.pragma.securityauth.config.Whitelist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter implements WebFilter {

    private final TokenProviderRepository tokenProvider;

    private final String userPath;

    private static final String TYPE_TOKEN = "Bearer ";

    private static final String TYPE_ROLE = "ROLE_";

    public JwtAuthenticationFilter(TokenProviderRepository tokenProvider, @Value("${routes.paths.login}") String userPath) {
        this.tokenProvider = tokenProvider;
        this.userPath = userPath;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if (path.equals(userPath)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(TYPE_TOKEN)) {
            String token = authHeader.substring(7);

            return tokenProvider.validateToken(token)
                    .flatMap(userSession -> {
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                userSession,
                                null,
                                List.of(new SimpleGrantedAuthority(TYPE_ROLE + userSession.getName()))
                        );
                        return chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                    })
                    .doOnNext(auth -> log.info("Authorized rol, token validated successfully"))
                    .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, ModelExceptionMessages.INVALID_TOKEN)));
        }

        return chain.filter(exchange);
    }
}
