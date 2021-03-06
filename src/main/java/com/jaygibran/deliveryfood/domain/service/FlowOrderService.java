package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.BusinessException;
import com.jaygibran.deliveryfood.domain.model.Order;
import com.jaygibran.deliveryfood.domain.model.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

import static com.jaygibran.deliveryfood.domain.model.OrderStatus.CANCELED;
import static com.jaygibran.deliveryfood.domain.model.OrderStatus.CONFIRMED;
import static com.jaygibran.deliveryfood.domain.model.OrderStatus.CREATED;
import static com.jaygibran.deliveryfood.domain.model.OrderStatus.DELIVERED;

@AllArgsConstructor
@Service
public class FlowOrderService {

    private final OrderRegistryService orderRegistryService;

    @Transactional
    public void confirm(String orderCode) {
        Order order = orderRegistryService.findOrFail(orderCode);
        order.confirm();
    }

    @Transactional
    public void deliver(String orderCode) {
        Order order = orderRegistryService.findOrFail(orderCode);
        order.deliver();
    }

    @Transactional
    public void cancel(String orderCode) {
        Order order = orderRegistryService.findOrFail(orderCode);
        order.cancel();
    }
}
