package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.api.assembler.RestaurantDTOAssembler;
import com.jaygibran.deliveryfood.api.model.CuisineDTO;
import com.jaygibran.deliveryfood.api.model.RestaurantDTO;
import com.jaygibran.deliveryfood.api.model.input.RestaurantInput;
import com.jaygibran.deliveryfood.core.validation.ValidationException;
import com.jaygibran.deliveryfood.domain.exception.BusinessException;
import com.jaygibran.deliveryfood.domain.exception.CuisineNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.repository.RestaurantRepository;
import com.jaygibran.deliveryfood.domain.service.RestaurantRegistryService;
import com.jaygibran.deliveryfood.domain.util.ObjectMerger;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
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
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private RestaurantRepository restaurantRepository;

    private RestaurantRegistryService restaurantRegistryService;

    private RestaurantDTOAssembler restaurantDTOAssembler;

    @GetMapping
    public List<RestaurantDTO> list() {
        return restaurantDTOAssembler.toCollectionDTO(this.restaurantRepository.findAll());
    }

    @GetMapping("/{id}")
    public RestaurantDTO search(@PathVariable Long id) {
        Restaurant restaurant = this.restaurantRegistryService.findOrFail(id);

        return restaurantDTOAssembler.toDTO(restaurant);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RestaurantDTO save(@RequestBody @Valid RestaurantInput restaurantInput) {
        try {
            Restaurant restaurant = toDomainObject(restaurantInput);
            return restaurantDTOAssembler.toDTO(this.restaurantRegistryService.save(restaurant));
        } catch (CuisineNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestaurantDTO update(@PathVariable Long id, @RequestBody @Valid RestaurantInput restaurantInput) {
        try {
            Restaurant restaurant = toDomainObject(restaurantInput);

            Restaurant restaurantToUpdate = this.restaurantRegistryService.findOrFail(id);

            BeanUtils.copyProperties(restaurant, restaurantToUpdate, "id", "paymentMethods", "address", "dateCreated", "products");

            return restaurantDTOAssembler.toDTO(this.restaurantRegistryService.save(restaurantToUpdate));
        } catch (CuisineNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.restaurantRegistryService.delete(id);
    }

    private Restaurant toDomainObject(RestaurantInput restaurantInput) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantInput.getName());
        restaurant.setFeeDelivery(restaurantInput.getDeliveryFee());

        Cuisine cuisine = new Cuisine();
        cuisine.setId(restaurantInput.getCuisine().getId());

        restaurant.setCuisine(cuisine);

        return restaurant;
    }
}
