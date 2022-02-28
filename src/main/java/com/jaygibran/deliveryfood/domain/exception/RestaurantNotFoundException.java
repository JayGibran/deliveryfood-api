package com.jaygibran.deliveryfood.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException {

    public static final String MSG_RESTAURANT_NOT_FOUND = "It doesn't exist any Restaurant with id: %d";

    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(Long id) {
        this(String.format(MSG_RESTAURANT_NOT_FOUND, id));
    }
}
