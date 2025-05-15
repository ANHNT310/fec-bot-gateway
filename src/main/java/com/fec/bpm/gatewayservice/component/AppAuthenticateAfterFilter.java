package com.fec.bpm.gatewayservice.component;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class AppAuthenticateAfterFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(authentication -> authentication instanceof JwtAuthenticationToken)
                .map(authentication -> (JwtAuthenticationToken) authentication)
                .flatMap(jwtAuth -> handleJwtAuthentication(jwtAuth, exchange, chain))
                .switchIfEmpty(chain.filter(exchange));
    }

    private Mono<Void> handleJwtAuthentication(JwtAuthenticationToken jwtAuth, ServerWebExchange exchange, WebFilterChain chain) {
        if (jwtAuth.getPrincipal() instanceof Jwt jwt) {
            String subject = jwt.getSubject();
            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                    .header("x-user-id", subject)
                    .build();
            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        }
        return chain.filter(exchange);
    }
}
