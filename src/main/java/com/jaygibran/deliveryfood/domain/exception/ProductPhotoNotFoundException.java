package com.jaygibran.deliveryfood.domain.exception;

public class ProductPhotoNotFoundException extends EntityNotFoundException {

    private static final String MSG_PRODUCT_PHOTO_NOT_FOUND = "It doesn't exist any photo for the product with id: %d from restaurant with id: %d";

    public ProductPhotoNotFoundException(String message) {
        super(message);
    }

    public ProductPhotoNotFoundException(Long productId, Long restaurantId) {
        this(String.format(MSG_PRODUCT_PHOTO_NOT_FOUND, productId, restaurantId));
    }
}
