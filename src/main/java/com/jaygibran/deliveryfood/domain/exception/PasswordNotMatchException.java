package com.jaygibran.deliveryfood.domain.exception;

public class PasswordNotMatchException extends BusinessException {

    public static final String MSG_PASSWORD_NOT_MATCH = "Password '%s' sent doesn't not match with current password";

    public PasswordNotMatchException(String password) {
        super(String.format(MSG_PASSWORD_NOT_MATCH, password));
    }
}
