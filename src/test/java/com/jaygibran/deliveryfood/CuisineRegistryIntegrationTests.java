package com.jaygibran.deliveryfood;

import com.jaygibran.deliveryfood.domain.exception.CuisineNotFoundException;
import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.service.CuisineRegistryService;
import com.jaygibran.deliveryfood.domain.service.RestaurantRegistryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CuisineRegistryIntegrationTests {

    @Autowired
    CuisineRegistryService cuisineRegistryService;

    @Autowired
    RestaurantRegistryService restaurantRegistryService;

    @Test
    public void saveCuisineSuccessful() {
        // given
        Cuisine cuisine = new Cuisine();
        cuisine.setName("Brazilian");

        // when
        Cuisine savedCuisine = cuisineRegistryService.save(cuisine);

        // then
        assertThat(savedCuisine).isNotNull();
        assertThat(savedCuisine.getId()).isNotNull();
    }

    @Test
    public void shouldFailsWhenTrySaveCuisineWithoutName() {
        // given
        Cuisine cuisine = new Cuisine();

        // when
        ConstraintViolationException error =
                assertThrows(ConstraintViolationException.class, () -> {
                    cuisineRegistryService.save(cuisine);
                });


        // then
        assertThat(error).isNotNull();
    }

    @Test
    public void shouldFailsWhenTryDeleteCuisineInUse() {
        // given
        Cuisine cuisine = new Cuisine();
        cuisine.setName("Brazilian");
        Cuisine savedCuisine = cuisineRegistryService.save(cuisine);
        Restaurant restaurant = new Restaurant();
        restaurant.setCuisine(cuisine);
        restaurant.setName("Brazilian Grill");
        restaurant.setFeeDelivery(BigDecimal.valueOf(10));
        restaurantRegistryService.save(restaurant);

        // when
        EntityInUseException entityInUseException = assertThrows(EntityInUseException.class, () -> {
            cuisineRegistryService.delete(savedCuisine.getId());
        });

        // then
        assertThat(entityInUseException).isNotNull();
    }

    @Test
    public void shouldFailsWhenTryDeleteCuisineWhichDoesNotExists() {
        // given
        Cuisine cuisine = new Cuisine();
        cuisine.setId(15645L);

        // when
        CuisineNotFoundException cuisineNotFoundException = assertThrows(CuisineNotFoundException.class, () -> {
            cuisineRegistryService.delete(cuisine.getId());
        });

        // then
        assertThat(cuisineNotFoundException).isNotNull();
    }
}
