package com.jaygibran.deliveryfood.jpa;

import com.jaygibran.deliveryfood.DeliveryfoodApiApplication;
import com.jaygibran.deliveryfood.domain.model.Kitchen;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.KitchenRepository;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class RestaurantQuery {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(DeliveryfoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RestaurantRepository restaurantRepository = applicationContext.getBean(RestaurantRepository.class);

        List<Restaurant> restaurants = restaurantRepository.all();
        restaurants.forEach(restaurant -> System.out.println("id: " + restaurant.getId() + " name: " + restaurant.getName() + " cozinha: " + restaurant.getKitchen().getName()));
    }
}
