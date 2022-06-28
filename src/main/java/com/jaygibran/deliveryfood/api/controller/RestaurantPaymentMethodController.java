package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.api.assembler.PaymentMethodDTOAssembler;
import com.jaygibran.deliveryfood.api.model.PaymentMethodDTO;
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
@RequestMapping("/restaurants/{restaurantId}/payment-methods")
public class RestaurantPaymentMethodController {

    private RestaurantRegistryService restaurantRegistryService;

    private PaymentMethodDTOAssembler paymentMethodDTOAssembler;

    @GetMapping
    public List<PaymentMethodDTO> list(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantRegistryService.findOrFail(restaurantId);
        return paymentMethodDTOAssembler.toCollectionDTO(restaurant.getPaymentMethods());
    }

    @DeleteMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
        restaurantRegistryService.disassociatePaymentMethod(restaurantId, paymentMethodId);
    }

    @PutMapping("/{paymentMethodId}")
    public void associate(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
        restaurantRegistryService.associatePaymentMethod(restaurantId, paymentMethodId);
    }
}
