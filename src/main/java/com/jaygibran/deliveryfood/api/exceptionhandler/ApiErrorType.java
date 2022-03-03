package com.jaygibran.deliveryfood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ApiErrorType {
    ENTITY_NOT_FOUND("/entity-not-found", "Entity not found"),
    ENTITY_IN_USE("/entity-in-user", "Entity in use"),
    BUSINESS_EXCEPTION("/business-exception", "Business exception");

    private String title;
    private String uri;

    ApiErrorType(String uri, String title) {
        this.uri = "https://deliveryfood.api.br" + uri;
        this.title = title;
    }

}
