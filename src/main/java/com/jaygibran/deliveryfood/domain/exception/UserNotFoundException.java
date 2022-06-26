package com.jaygibran.deliveryfood.domain.exception;

public class UserNotFoundException extends EntityNotFoundException {

    public static final String MSG_NO_USER_FOUND = "It doesn't exist any user with id: %d";

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long id) {
        this(String.format(MSG_NO_USER_FOUND, id));
    }
}
