package com.jaygibran.deliveryfood.core.security.authorizationserver;

import java.util.Map;

import com.nimbusds.jose.jwk.JWKSet;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JwkSetController {

    private final JWKSet jwkSet;

    @GetMapping("/.well-known/jks.json")
    public Map<String, Object> keys() {
        System.out.println("JWS ENDPOINT");
        return jwkSet.toJSONObject();
    }
}
