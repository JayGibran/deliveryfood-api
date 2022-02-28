package com.jaygibran.deliveryfood.domain.exception;

public class StateNotFoundException extends EntityNotFoundException {

    public static final String MSG_NO_STATE_FOUND = "It doesn't exist any state with id: %d";

    public StateNotFoundException(String message) {
        super(message);
    }

    public StateNotFoundException(Long id) {
        this(String.format(MSG_NO_STATE_FOUND, id));
    }
}
