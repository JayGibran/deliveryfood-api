package com.jaygibran.deliveryfood.domain.exception;

public class CuisineNotFoundException extends EntityNotFoundException {

    public static final String MSG_CUISINE_NOT_FOUND = "It doesn't exist any cuisine with id: %d";

    public CuisineNotFoundException(String message) {
        super(message);
    }

    public CuisineNotFoundException(Long id) {
        this(String.format(MSG_CUISINE_NOT_FOUND, id));
    }
}
