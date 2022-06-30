package com.jaygibran.deliveryfood.domain.exception;

public class ProductNotFoundException extends EntityNotFoundException {

    private static final String MSG_PRODUCT_NOT_FOUND = "It doesn't exist any saved product with id: %d to restaurant with id: %d";

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Long id, Long restaurantId) {
        this(String.format(MSG_PRODUCT_NOT_FOUND, id, restaurantId));
    }
}
