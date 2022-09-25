package com.jaygibran.deliveryfood.domain.event;

import com.jaygibran.deliveryfood.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderConfirmedEvent {
    
    private final Order order;
}
