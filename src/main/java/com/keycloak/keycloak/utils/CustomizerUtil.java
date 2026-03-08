package com.keycloak.keycloak.utils;

import com.keycloak.keycloak.converter.KeycloakConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomizerUtil {

    private final KeycloakConverter keycloakConverter;

    public  Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer> jwtCustomizer = new Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer>() {
        @Override
        public void customize(OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer jwtConfigurer) {
            jwtConfigurer.jwtAuthenticationConverter(keycloakConverter);
        }

    };
}
