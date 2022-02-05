package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Kitchen;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.KitchenRepository;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RestaurantRegistryService {

    private RestaurantRepository restaurantRepository;

    private KitchenRepository kitchenRepository;

    public Restaurant save(Restaurant restaurant){
        Long kitchenId = restaurant.getKitchen().getId();
        Kitchen kitchen = kitchenRepository.findById(kitchenId);
        if(kitchen == null){
            throw new EntityNotFoundException(String.format("It doesn't exist any kitchen with id: %d", kitchenId));
        }
        restaurant.setKitchen(kitchen);

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
