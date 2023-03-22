package com.jaygibran.deliveryfood.core.security.authorizationserver;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties("deliveryfood.jwt.keystore")
public class JwtKeyStoreProperties {

    @NotNull
    private Resource jksLocation;
    @NotBlank
    private String pass;
    @NotBlank
    private String alias;
}
