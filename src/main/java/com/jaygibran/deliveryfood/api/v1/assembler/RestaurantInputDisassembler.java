package com.jaygibran.deliveryfood.api.v1.assembler;

import com.jaygibran.deliveryfood.api.v1.model.input.RestaurantInput;
import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RestaurantInputDisassembler {

    private final ModelMapper modelMapper;

    public Restaurant toDomainObject(RestaurantInput restaurantInput) {
        return modelMapper.map(restaurantInput, Restaurant.class);
    }

    public void copyToDomainObject(RestaurantInput restaurantInput, Restaurant restaurant) {
        // To avoid org.hibernate.HibernateException: identifier of an instance of Cuisine was altered from 1 to 2
        restaurant.setCuisine(new Cuisine());
        if (restaurant.getAddress() != null) {
            restaurant.getAddress().setCity(null);
        }
        modelMapper.map(restaurantInput, restaurant);
    }
}
