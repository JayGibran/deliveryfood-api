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

    public static final String ORDER_STATUS_ERROR_MSG = "Status of order %d can't be changed from %s to %s";
    private final OrderRegistryService orderRegistryService;

    @Transactional
    public void confirm(Long orderId) {
        Order order = orderRegistryService.findOrFail(orderId);

        changeOrderStatusFromTo(order, CREATED, CONFIRMED);
    }

    @Transactional
    public void deliver(Long orderId) {
        Order order = orderRegistryService.findOrFail(orderId);

        changeOrderStatusFromTo(order, CONFIRMED, DELIVERED);
    }

    @Transactional
    public void cancel(Long orderId) {
        Order order = orderRegistryService.findOrFail(orderId);

        changeOrderStatusFromTo(order, CREATED, CANCELED);
    }

    private void changeOrderStatusFromTo(Order order, OrderStatus from, OrderStatus to) {
        if (!order.getStatus().equals(from)) {
            throw new BusinessException(String.format(ORDER_STATUS_ERROR_MSG,
                    order.getId(), order.getStatus().getDescription(), to.getDescription()));
        }

        order.setStatus(to);
        order.setDateConfirmation(OffsetDateTime.now());
    }
}
