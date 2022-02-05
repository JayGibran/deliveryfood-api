package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepository;
import com.jaygibran.deliveryfood.domain.service.RestaurantRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private RestaurantRepository restaurantRepository;

    private RestaurantRegistryService restaurantRegistryService;

    @GetMapping
    public List<Restaurant> list(){
        return this.restaurantRepository.all();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> search(@PathVariable Long id){
        Restaurant restaurant = this.restaurantRepository.findById(id);
        if(restaurant == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurant);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Restaurant restaurant){
        try{
            Restaurant savedRestaurant = this.restaurantRegistryService.save(restaurant);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRestaurant);
        }catch(EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Restaurant restaurant){
        Restaurant restaurantToUpdate = this.restaurantRepository.findById(id);
        if(restaurantToUpdate == null){
            return ResponseEntity.notFound().build();
        }
        BeanUtils.copyProperties(restaurant, restaurantToUpdate, "id");
        try{
            Restaurant updatedRestaurant = this.restaurantRegistryService.save(restaurantToUpdate);
            return ResponseEntity.ok(updatedRestaurant);
        }catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Restaurant> delete(@PathVariable Long id){
        try{
            this.restaurantRegistryService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
