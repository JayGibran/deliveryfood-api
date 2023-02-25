package com.jaygibran.deliveryfood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ApiErrorType {
    RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
    ENTITY_IN_USE("/entity-in-user", "Entity in use"),
    BUSINESS_EXCEPTION("/business-exception", "Business exception"),
    MESSAGE_NOT_READABLE("/message-not-readable", "Message not readable"),
    INVALID_PARAMETER("/invalid-parameter", "Invalid Parameter"),
    SYSTEM_ERROR("/system-error", "System error"),
    INVALID_DATA("/invalid-data", "Invalid data"),
    ACCESS_DENIED("/access-denied", "Access is denied");

    private String title;
    private String uri;

    ApiErrorType(String uri, String title) {
        this.uri = "https://deliveryfood.api.br" + uri;
        this.title = title;
    }

}
