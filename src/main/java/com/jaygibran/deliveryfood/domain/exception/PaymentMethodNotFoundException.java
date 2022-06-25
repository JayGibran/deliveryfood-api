package com.jaygibran.deliveryfood.domain.exception;

public class PaymentMethodNotFoundException extends EntityNotFoundException {

    public static final String MSG_PAYMENT_METHOD_NOT_FOUND = "It doesn't exist any payment method with id: %d";

    public PaymentMethodNotFoundException(String message) {
        super(message);
    }

    public PaymentMethodNotFoundException(Long id) {
        this(String.format(MSG_PAYMENT_METHOD_NOT_FOUND, id));
    }
}
