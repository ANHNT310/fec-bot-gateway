package com.fec.bot.gateway.configuration;

import com.fec.bot.gateway.component.UserHeaderGatewayFilter;
import com.fec.bot.gateway.properties.RouteUriProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final RouteUriProperties routeUriProperties;
    private final UserHeaderGatewayFilter userHeaderGatewayFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("authentication-service", r -> r
                        .path("/authentication/**")
                        .filters(f -> f
                                .filter(userHeaderGatewayFilter)
                                .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_FIRST")
                                .requestRateLimiter(config -> {
                                    config.setRateLimiter(redisRateLimiter());
                                    config.setKeyResolver(ipKeyResolver());
                                })
                                .circuitBreaker(cb -> cb
                                        .setName("authCB")
                                        .setFallbackUri("forward:/fallback/authentication"))
                                .retry(retry -> retry
                                        .setRetries(2)
                                        .setStatuses(HttpStatus.BAD_GATEWAY, HttpStatus.INTERNAL_SERVER_ERROR)
                                        .setMethods(HttpMethod.GET, HttpMethod.POST))
                        )
                        .uri(routeUriProperties.getUriById("authentication-service"))
                )
                .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // dùng addAllowedOriginPattern thay vì addAllowedOrigin cho wildcard
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

    // Redis-based rate limiter: 2 requests/sec, burst of 4
    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(2, 4);
    }

    // KeyResolver by IP
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> {
            SocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
            if (remoteAddress instanceof InetSocketAddress inetSocketAddress) {
                InetAddress address = inetSocketAddress.getAddress();
                if (address != null) {
                    return Mono.just(address.getHostAddress());
                }
            }
            return Mono.just("unknown");
        };
    }
}
