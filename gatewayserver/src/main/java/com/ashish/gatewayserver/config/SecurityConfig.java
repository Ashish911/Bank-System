package com.ashish.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {


    /**
     * Configures the security rules and settings for the web application using {@link ServerHttpSecurity}.
     * This method defines the access control policies for various endpoints and sets up OAuth2 resource server
     * for processing JWT authentication. Additionally, Cross-Site Request Forgery (CSRF) protection is disabled.
     *
     * @param httpSecurity the {@link ServerHttpSecurity} to configure security rules and features
     * @return the configured {@link SecurityWebFilterChain} instance
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity.authorizeExchange(
                exchanges -> exchanges
                        .pathMatchers(HttpMethod.GET).permitAll()
                        .pathMatchers("/eazybank/accounts/**").authenticated()
                        .pathMatchers("/eazybank/loans/**").authenticated()
                        .pathMatchers("/eazybank/cards/**").authenticated())
        .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                .jwt(Customizer.withDefaults()));

        httpSecurity.csrf(csrfSpec -> csrfSpec.disable());

        return httpSecurity.build();
    }

}
