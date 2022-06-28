package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.CuisineNotFoundException;
import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.exception.RestaurantNotFoundException;
import com.jaygibran.deliveryfood.domain.model.City;
import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.model.PaymentMethod;
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
    private final RestaurantRepository restaurantRepository;
    private final CuisineRegistryService cuisineRegistryService;
    private final CityRegistryService cityRegistryService;
    private final PaymentMethodRegistryService paymentMethodRegistryService;

    @Transactional
    public Restaurant save(Restaurant restaurant) {
        Long cuisineId = restaurant.getCuisine().getId();
        Long cityId = restaurant.getAddress().getCity().getId();

        Cuisine cuisine = cuisineRegistryService.searchOrFail(cuisineId);
        City city = cityRegistryService.findOrFail(cityId);

        restaurant.setCuisine(cuisine);
        restaurant.getAddress().setCity(city);

        return this.restaurantRepository.save(restaurant);
    }

    @Transactional
    public void activate(Long restaurantId) {
        Restaurant restaurant = findOrFail(restaurantId);
        restaurant.activate();
    }

    @Transactional
    public void inactivate(Long restaurantId) {
        Restaurant restaurant = findOrFail(restaurantId);
        restaurant.inactivate();
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

    @Transactional
    public void disassociatePaymentMethod(Long restaurantId, Long paymentMethodId) {
        Restaurant restaurant = findOrFail(restaurantId);
        PaymentMethod paymentMethod = paymentMethodRegistryService.searchOrFail(paymentMethodId);
        restaurant.removePaymentMethod(paymentMethod);
    }

    @Transactional
    public void associatePaymentMethod(Long restaurantId, Long paymentMethodId) {
        Restaurant restaurant = findOrFail(restaurantId);
        PaymentMethod paymentMethod = paymentMethodRegistryService.searchOrFail(paymentMethodId);
        restaurant.addPaymentMethod(paymentMethod);
    }

    public Restaurant findOrFail(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }
}
