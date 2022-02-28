package com.jaygibran.deliveryfood.domain.exception;

public class CityNotFoundException extends EntityNotFoundException {
    
    public static final String MSG_CITY_NOT_FOUND = "It doesn't exist any city with id: %d";

    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(Long id) {
        this(String.format(MSG_CITY_NOT_FOUND, id));
    }
}
