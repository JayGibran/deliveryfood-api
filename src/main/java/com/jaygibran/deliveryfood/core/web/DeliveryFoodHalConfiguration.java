package com.jaygibran.deliveryfood.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.http.MediaType;

@Configuration
public class DeliveryFoodHalConfiguration {
    @Bean
    public HalConfiguration globalPolicy() {
        return new HalConfiguration()
                .withMediaType(MediaType.APPLICATION_JSON)
                .withMediaType(DeliveryFoodMediaTypes.V1_APPLICATION_JSON)
                .withMediaType(DeliveryFoodMediaTypes.V2_APPLICATION_JSON);
    }
}
