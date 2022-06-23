package com.jaygibran.deliveryfood.api.assembler;

import com.jaygibran.deliveryfood.api.model.input.RestaurantInput;
import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantInputDisassembler {

    public Restaurant toDomainObject(RestaurantInput restaurantInput) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantInput.getName());
        restaurant.setFeeDelivery(restaurantInput.getDeliveryFee());

        Cuisine cuisine = new Cuisine();
        cuisine.setId(restaurantInput.getCuisine().getId());

        restaurant.setCuisine(cuisine);

        return restaurant;
    }
}
