package com.jaygibran.deliveryfood.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException {

    private static final String MSG_ORDER_NOT_FOUND = "It doesn't exist any order with id: %s";

    public OrderNotFoundException(String orderCode) {
        super(String.format(MSG_ORDER_NOT_FOUND, orderCode));
    }
}
