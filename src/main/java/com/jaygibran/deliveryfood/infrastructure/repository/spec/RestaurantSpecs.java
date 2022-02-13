package com.jaygibran.deliveryfood.infrastructure.repository.spec;

import com.jaygibran.deliveryfood.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantSpecs {

    public static Specification<Restaurant> withFreeDelivery() {
        return ((root, query, builder) -> builder.equal(root.get("feeDelivery"), BigDecimal.ZERO));
    }

    public static Specification<Restaurant> withSimilarName(String name) {
        return ((root, query, builder) -> builder.like(root.get("name"), "%" + name + "%"));
    }
}
