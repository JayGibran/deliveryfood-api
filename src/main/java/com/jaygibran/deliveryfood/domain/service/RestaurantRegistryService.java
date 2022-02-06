package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.CuisineRepository;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RestaurantRegistryService {

    private RestaurantRepository restaurantRepository;

    private CuisineRepository cuisineRepository;

    public Restaurant save(Restaurant restaurant){
        Long kitchenId = restaurant.getCuisine().getId();
        Cuisine cuisine = cuisineRepository.findById(kitchenId);
        if(cuisine == null){
            throw new EntityNotFoundException(String.format("It doesn't exist any kitchen with id: %d", kitchenId));
        }
        restaurant.setCuisine(cuisine);

        return this.restaurantRepository.save(restaurant);
    }

    public void delete(Long id) {
        try{
            this.restaurantRepository.delete(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(String.format("It doesn't exist any kitchen with id: %d", id));
        }
    }
}
