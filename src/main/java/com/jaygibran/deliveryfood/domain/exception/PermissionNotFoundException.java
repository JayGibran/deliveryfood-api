package com.jaygibran.deliveryfood.domain.exception;

public class PermissionNotFoundException extends EntityNotFoundException {

    public static final String MSG_PERMISSION_NOT_FOUND = "It doesn't exist any permission with id: %d";

    public PermissionNotFoundException(String message) {
        super(message);
    }

    public PermissionNotFoundException(Long id) {
        this(String.format(MSG_PERMISSION_NOT_FOUND, id));
    }
}
