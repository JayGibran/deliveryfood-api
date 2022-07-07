package com.jaygibran.deliveryfood.domain.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum OrderStatus {
    CREATED("Created"),
    CONFIRMED("Confirmed", CREATED),
    DELIVERED("Delivered", CONFIRMED),
    CANCELED("Canceled", CREATED);

    private String description;
    private List<OrderStatus> previousStatus;

    private OrderStatus(String description, OrderStatus... previousStatus) {
        this.description = description;
        this.previousStatus = Arrays.asList(previousStatus);
    }

    public boolean canNotChangeTo(OrderStatus newOrderStatus) {
        return !newOrderStatus.previousStatus.contains(this);
    }
}
