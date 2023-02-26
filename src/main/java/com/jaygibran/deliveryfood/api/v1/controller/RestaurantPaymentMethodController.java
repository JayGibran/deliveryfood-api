package com.jaygibran.deliveryfood.api.v1.controller;

import com.jaygibran.deliveryfood.api.v1.assembler.PaymentMethodDTOAssembler;
import com.jaygibran.deliveryfood.api.v1.model.PaymentMethodDTO;
import com.jaygibran.deliveryfood.core.security.CheckSecurity;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.service.RestaurantRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(path = "/v1/restaurants/{restaurantId}/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantPaymentMethodController {

    private RestaurantRegistryService restaurantRegistryService;

    private PaymentMethodDTOAssembler paymentMethodDTOAssembler;

    @CheckSecurity.Restaurants.AllowQuery
    @GetMapping
    public List<PaymentMethodDTO> list(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantRegistryService.findOrFail(restaurantId);
        return paymentMethodDTOAssembler.toCollectionDTO(restaurant.getPaymentMethods());
    }

    @CheckSecurity.Restaurants.AllowManageFunctioning
    @DeleteMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
        restaurantRegistryService.disassociatePaymentMethod(restaurantId, paymentMethodId);
    }

    @CheckSecurity.Restaurants.AllowManageFunctioning
    @PutMapping("/{paymentMethodId}")
    public void associate(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
        restaurantRegistryService.associatePaymentMethod(restaurantId, paymentMethodId);
    }
}
