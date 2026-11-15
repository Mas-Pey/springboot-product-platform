package com.project.api_gateway.config;

import com.project.api_gateway.util.JwtUtil;
import com.project.api_gateway.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange(ex -> ex
                        .pathMatchers("/auth/**").permitAll()
                        .pathMatchers("/product/internal/**").denyAll()
                        .anyExchange().authenticated())
                .exceptionHandling(e -> e
                        .authenticationEntryPoint((exchange, err) ->
                                ResponseUtil.writeError(exchange, HttpStatus.UNAUTHORIZED,
                                        "Token tidak ada atau tidak valid"))
                        .accessDeniedHandler((exchange, err) ->
                                ResponseUtil.writeError(exchange, HttpStatus.FORBIDDEN,
                                        "Endpoint internal tidak dapat diakses melalui Gateway")))
                .addFilterAt(new JwtAuthenticationFilter(jwtUtil), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}