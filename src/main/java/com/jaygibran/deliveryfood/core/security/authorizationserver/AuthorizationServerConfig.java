package com.jaygibran.deliveryfood.core.security.authorizationserver;

import java.io.*;
import java.security.*;
import java.time.*;
import java.util.*;

import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.source.*;
import com.nimbusds.jose.proc.*;
import org.springframework.context.annotation.*;
import org.springframework.core.*;
import org.springframework.core.annotation.*;
import org.springframework.core.io.*;
import org.springframework.jdbc.core.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.*;
import org.springframework.security.oauth2.server.authorization.config.*;
import org.springframework.security.web.*;

@Configuration
public class AuthorizationServerConfig {
    
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authFilterChain(HttpSecurity httpSecurity) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
        
        return httpSecurity.build();
    }
        
    @Bean
    public ProviderSettings providerSettings(DeliveryFoodSecurityProperties properties) {
        return ProviderSettings.builder()
                .issuer(properties.getProviderUrl())
                .build();
        
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {

        RegisteredClient algafoodbackend = RegisteredClient
                .withId("1")
                .clientId("deliveryfood-backend")
                .clientSecret(passwordEncoder.encode("backend123"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("READ")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                        .accessTokenTimeToLive(Duration.ofMinutes(30))
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(Arrays.asList(algafoodbackend));
    }
    
    @Bean
    public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcOperations jdbcOperations, 
                                                                 RegisteredClientRepository registeredClientRepository){
        return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
    }
    
    @Bean
    public JWKSource<SecurityContext> jwkSource(JwtKeyStoreProperties properties) throws Exception{
        char[] keyStorePass = properties.getPass().toCharArray();
        String keyPairAlias = properties.getAlias();
        
        Resource jksLocation = properties.getJksLocation();
        InputStream inputStream = jksLocation.getInputStream();
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(inputStream, keyStorePass);

        RSAKey rsaKey = RSAKey.load(keyStore, keyPairAlias, keyStorePass);
        
        return new ImmutableJWKSet<>(new JWKSet(rsaKey));
    }
}
