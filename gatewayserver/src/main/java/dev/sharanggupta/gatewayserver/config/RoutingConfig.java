package dev.sharanggupta.gatewayserver.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class RoutingConfig {
    @Bean
    public RouteLocator eazyBankRoute(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("account_route", r -> r
                        .path("/eazybank/account/**")
                        .filters(f->f
                                .rewritePath("/eazybank/account/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("account").setFallbackUri("forward:/contactSupport")))
                        .uri("lb://ACCOUNT"))
                .route("card_route", r -> r
                        .path("/eazybank/card/**")
                        .filters(f->f
                                .rewritePath("/eazybank/card/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("card").setFallbackUri("forward:/contactSupport")))
                        .uri("lb://CARD"))
                .route("loan_route", r -> r
                        .path("/eazybank/loan/**")
                        .filters(f->f
                                .rewritePath("/eazybank/loan/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("loan").setFallbackUri("forward:/contactSupport")))
                        .uri("lb://LOAN"))
                .build();
    }
}
