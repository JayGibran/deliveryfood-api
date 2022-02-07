package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.CuisineRepository;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class RestaurantRegistryService {

    private RestaurantRepository restaurantRepository;

    private CuisineRepository cuisineRepository;

    public Restaurant save(Restaurant restaurant) {
        Long cuisineId = restaurant.getCuisine().getId();
        Cuisine cuisine = cuisineRepository.findById(cuisineId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("It doesn't exist any Cuisine with id: %d", cuisineId)));

        restaurant.setCuisine(cuisine);

        return this.restaurantRepository.save(restaurant);
    }

    public void delete(Long id) {
        try {
            this.restaurantRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(String.format("It doesn't exist any Cuisine with id: %d", id));
        }
    }
}
