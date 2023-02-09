package com.jaygibran.deliveryfood.api.v1.controller;

import com.jaygibran.deliveryfood.api.v1.assembler.UserDTOAssembler;
import com.jaygibran.deliveryfood.api.v1.model.UserDTO;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.service.RestaurantRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/v1/restaurants/{restaurantId}/responsible", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantUserController {

    private RestaurantRegistryService restaurantRegistryService;

    private UserDTOAssembler userDTOAssembler;

    @GetMapping
    public CollectionModel<UserDTO> list(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantRegistryService.findOrFail(restaurantId);
        return userDTOAssembler.toCollectionModel(restaurant.getUsers())
                .removeLinks()
                .add(linkTo(methodOn(RestaurantUserController.class).list(restaurantId)).withSelfRel());
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
