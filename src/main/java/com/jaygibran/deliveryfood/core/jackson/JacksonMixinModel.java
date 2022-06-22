package com.jaygibran.deliveryfood.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jaygibran.deliveryfood.api.model.mixin.CityMixin;
import com.jaygibran.deliveryfood.api.model.mixin.CuisineMixin;
import com.jaygibran.deliveryfood.api.model.mixin.OrderMixin;
import com.jaygibran.deliveryfood.domain.model.City;
import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.model.Order;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModel extends SimpleModule {

    public JacksonMixinModel() {
        setMixInAnnotation(City.class, CityMixin.class);
        setMixInAnnotation(Cuisine.class, CuisineMixin.class);
        setMixInAnnotation(Order.class, OrderMixin.class);
    }
}
