package com.jaygibran.deliveryfood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jaygibran.deliveryfood.api.model.mixin.RestaurantMixin;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModel extends SimpleModule {

    public JacksonMixinModel() {
        setMixInAnnotation(Restaurant.class, RestaurantMixin.class);
    }
}
