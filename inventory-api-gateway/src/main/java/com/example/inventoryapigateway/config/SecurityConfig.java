package com.example.inventoryapigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.oauth2.jwt.Jwt;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;

import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {

        return http

                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .authorizeExchange(exchanges -> exchanges

                        // =========================================
                        // PUBLIC ENDPOINTS
                        // =========================================

                        .pathMatchers("/actuator/**")
                        .permitAll()

                        // =========================================
                        // ADMIN ONLY
                        // =========================================

                        .pathMatchers("/items", "/items/**")
                        .hasRole("ADMIN")

                        .pathMatchers("/stocks", "/stocks/**")
                        .hasRole("ADMIN")

                        // =========================================
                        // USER + ADMIN
                        // =========================================

                        .pathMatchers("/inventory", "/inventory/**")
                        .hasAnyRole("USER", "ADMIN")

                        .pathMatchers(
                                "/inventoryElasticSearch",
                                "/inventoryElasticSearch/**"
                        )
                        .hasAnyRole("USER", "ADMIN")

                        // =========================================
                        // ALL OTHER REQUESTS
                        // =========================================

                        .anyExchange()
                        .authenticated()
                )

                // =========================================
                // OAUTH2 RESOURCE SERVER
                // =========================================

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(
                                        jwtAuthenticationConverter()
                                )
                        )
                )

                .build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverterAdapter
    jwtAuthenticationConverter() {

        JwtAuthenticationConverter jwtAuthenticationConverter =
                new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                this::extractAuthorities
        );

        return new ReactiveJwtAuthenticationConverterAdapter(
                jwtAuthenticationConverter
        );
    }

    private Collection<GrantedAuthority>
    extractAuthorities(Jwt jwt) {

        Map<String, Object> realmAccess =
                jwt.getClaim("realm_access");

        System.out.println("REALM ACCESS = " + realmAccess);

        if (realmAccess == null ||
                realmAccess.get("roles") == null) {

            return List.of();
        }

        List<String> roles =
                (List<String>) realmAccess.get("roles");

        System.out.println("ROLES = " + roles);

        return roles.stream()
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}