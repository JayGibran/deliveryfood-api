package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.CuisineNotFoundException;
import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.exception.RestaurantNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class RestaurantRegistryService {

    private static final String MSG_RESTAURANT_BEING_USED = "Restaurant of id %d can't be removed because is being used";
    private RestaurantRepository restaurantRepository;
    private CuisineRegistryService cuisineRegistryService;

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        Long cuisineId = restaurant.getCuisine().getId();
        Cuisine cuisine = cuisineRegistryService.searchOrFail(cuisineId);

        restaurant.setCuisine(cuisine);

        return this.restaurantRepository.save(restaurant);
    }

    @Transactional
    public void delete(Long id) {
        try {
            this.restaurantRepository.deleteById(id);
            this.restaurantRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new CuisineNotFoundException(id);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException(String.format(MSG_RESTAURANT_BEING_USED, id));
        }
    }

    public Restaurant findOrFail(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }
}
