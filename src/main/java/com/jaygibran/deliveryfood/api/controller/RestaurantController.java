package com.jaygibran.deliveryfood.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepository;
import com.jaygibran.deliveryfood.domain.service.RestaurantRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private RestaurantRepository restaurantRepository;

    private RestaurantRegistryService restaurantRegistryService;

    @GetMapping
    public List<Restaurant> list() {
        return this.restaurantRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> search(@PathVariable Long id) {
        Optional<Restaurant> restaurant = this.restaurantRepository.findById(id);
        if (restaurant.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurant.get());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Restaurant restaurant) {
        try {
            Restaurant savedRestaurant = this.restaurantRegistryService.save(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRestaurant);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        Optional<Restaurant> restaurantToUpdate = this.restaurantRepository.findById(id);
        if (restaurantToUpdate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        BeanUtils.copyProperties(restaurant, restaurantToUpdate.get(), "id", "paymentMethods", "address", "dateCreated", "products");
        try {
            Restaurant updatedRestaurant = this.restaurantRegistryService.save(restaurantToUpdate.get());
            return ResponseEntity.ok(updatedRestaurant);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Restaurant> delete(@PathVariable Long id) {
        try {
            this.restaurantRegistryService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> merge(@PathVariable Long id, @RequestBody Map<String, Object> mapValues) {
        Optional<Restaurant> restaurantToUpdate = this.restaurantRepository.findById(id);
        if (restaurantToUpdate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        merge(mapValues, restaurantToUpdate.get());

        return update(id, restaurantToUpdate.get());
    }

    public void merge(Map<String, Object> originData, Restaurant restaurantToUpdate) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurant restaurantOrigen = objectMapper.convertValue(originData, Restaurant.class);

        originData.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Restaurant.class, key);
            if (field != null) {
                field.setAccessible(true);

                Object newValue = ReflectionUtils.getField(field, restaurantOrigen);

                ReflectionUtils.setField(field, restaurantToUpdate, newValue);
            }
        });
    }
}
