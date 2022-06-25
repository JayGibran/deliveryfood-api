package com.jaygibran.deliveryfood.domain.exception;

public class GroupNotFoundException extends EntityNotFoundException {

    public static final String MSG_GROUP_NOT_FOUND = "It doesn't exist any group with id: %d";

    public GroupNotFoundException(String message) {
        super(message);
    }

    public GroupNotFoundException(Long id) {
        this(String.format(MSG_GROUP_NOT_FOUND, id));
    }
}
