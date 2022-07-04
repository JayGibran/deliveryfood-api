package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.api.assembler.UserDTOAssembler;
import com.jaygibran.deliveryfood.api.model.PaymentMethodDTO;
import com.jaygibran.deliveryfood.api.model.UserDTO;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.service.RestaurantRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurants/{restaurantId}/responsible")
public class RestaurantUserController {

    private RestaurantRegistryService restaurantRegistryService;

    private UserDTOAssembler userDTOAssembler;

    @GetMapping
    public List<UserDTO> list(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantRegistryService.findOrFail(restaurantId);
        return userDTOAssembler.toCollectionDTO(restaurant.getUsers());
    }

    @PutMapping("/{userId}")
    public void associate(@PathVariable Long restaurantId, @PathVariable Long userId) {
        restaurantRegistryService.associateUser(restaurantId, userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long restaurantId, @PathVariable Long userId) {
        restaurantRegistryService.disassociateUser(restaurantId, userId);
    }
}
