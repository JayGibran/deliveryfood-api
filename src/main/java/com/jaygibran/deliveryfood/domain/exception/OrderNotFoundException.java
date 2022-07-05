package com.jaygibran.deliveryfood.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException {

    private static final String MSG_ORDER_NOT_FOUND = "It doesn't exist any order with id: %d";

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(Long id) {
        this(String.format(MSG_ORDER_NOT_FOUND, id));
    }
}
