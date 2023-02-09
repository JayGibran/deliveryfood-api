package com.jaygibran.deliveryfood.api.v1.controller;

import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.CuisineRepository;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final RestaurantRepository restaurantRepository;

    private final CuisineRepository cuisineRepository;

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
        return restaurantRepository.findWithFreeDelivery(name);
    }

    @GetMapping("/restaurants/first")
    public Optional<Restaurant> firstRestaurant() {
        return restaurantRepository.searchFirst();
    }

    @GetMapping("/cuisine/first")
    public Optional<Cuisine> firstCuisine() {
        return cuisineRepository.searchFirst();
    }
}
