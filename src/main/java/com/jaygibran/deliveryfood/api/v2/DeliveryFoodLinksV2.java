package com.jaygibran.deliveryfood.api.v2;

import com.jaygibran.deliveryfood.api.v2.controller.CityControllerV2;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class DeliveryFoodLinksV2 {

    public Link linkToCities(String rel) {
        return linkTo(CityControllerV2.class).withRel(rel);
    }

    public Link linkToCities() {
        return linkToCities(IanaLinkRelations.SELF.value());
    }
}