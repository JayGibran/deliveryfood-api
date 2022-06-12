package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.domain.exception.BusinessException;
import com.jaygibran.deliveryfood.domain.exception.CuisineNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepository;
import com.jaygibran.deliveryfood.domain.service.RestaurantRegistryService;
import com.jaygibran.deliveryfood.domain.util.ObjectMerger;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
    public Restaurant search(@PathVariable Long id) {
        return this.restaurantRegistryService.findOrFail(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Restaurant save(@RequestBody @Valid Restaurant restaurant) {
        try {
            return this.restaurantRegistryService.save(restaurant);
        } catch (CuisineNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id, @RequestBody @Valid Restaurant restaurant) {
        try {
            Restaurant restaurantToUpdate = this.restaurantRegistryService.findOrFail(id);

            BeanUtils.copyProperties(restaurant, restaurantToUpdate, "id", "paymentMethods", "address", "dateCreated", "products");

            return this.restaurantRegistryService.save(restaurantToUpdate);
        } catch (CuisineNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.restaurantRegistryService.delete(id);
    }

    @PatchMapping("/{id}")
    public Restaurant merge(@PathVariable Long id, @RequestBody Map<String, Object> mapValues, HttpServletRequest request) {
        Restaurant restaurantToUpdate = this.restaurantRegistryService.findOrFail(id);

        ObjectMerger<Restaurant> objectMerger = new ObjectMerger<>(Restaurant.class);

        objectMerger.merge(mapValues, restaurantToUpdate, request);

        return update(id, restaurantToUpdate);
    }
}
