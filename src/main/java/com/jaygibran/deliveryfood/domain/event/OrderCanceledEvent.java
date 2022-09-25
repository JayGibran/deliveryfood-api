package com.jaygibran.deliveryfood.domain.event;

import com.jaygibran.deliveryfood.domain.model.Order;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderCanceledEvent {
    
    private final Order order;
}
