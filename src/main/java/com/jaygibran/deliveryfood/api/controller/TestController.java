package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepository;
import com.jaygibran.deliveryfood.infrastructure.repository.spec.RestaurantWithFreeDelivery;
import com.jaygibran.deliveryfood.infrastructure.repository.spec.RestaurantWithSimilarName;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final RestaurantRepository restaurantRepository;

    @GetMapping("/restaurants/by-name")
    public List<Restaurant> restaurantsByName(
            String name, Long cuisineId) {
        return restaurantRepository.searchByName(name, cuisineId);
    }

    @GetMapping("/restaurants/by-name-and-fee-delivery")
    public List<Restaurant> restaurantsByNameAndFeeDelivery(String name, BigDecimal feeDeliveryMin, BigDecimal feeDeliveryMax) {
        return restaurantRepository.find(name, feeDeliveryMin, feeDeliveryMax);
    }

    @GetMapping("/restaurants/with-free-delivery")
    public List<Restaurant> restaurantsByFreeDelivery(String name) {
        var withFreeDelivery = new RestaurantWithFreeDelivery();
        var withSimilarName = new RestaurantWithSimilarName(name);

        return restaurantRepository.findAll(withSimilarName.and(withFreeDelivery));
    }
}
