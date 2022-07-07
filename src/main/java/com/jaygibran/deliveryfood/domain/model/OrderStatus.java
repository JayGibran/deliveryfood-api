package com.jaygibran.deliveryfood.domain.model;

import lombok.Getter;

@Getter
public enum OrderStatus {
    CREATED("Created"),
    CONFIRMED("Confirmed"),
    DELIVERED("Delivered"),
    CANCELED("Canceled");

    String description;

    private OrderStatus(String description) {
        this.description = description;
    }
}
