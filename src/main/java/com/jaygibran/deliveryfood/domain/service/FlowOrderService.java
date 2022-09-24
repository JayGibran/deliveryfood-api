package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class FlowOrderService {

    private final OrderRegistryService orderRegistryService;
    private final EmailService emailService;

    @Transactional
    public void confirm(String orderCode) {
        Order order = orderRegistryService.findOrFail(orderCode);
        order.confirm();

        EmailService.Message message = EmailService.Message.builder()
                .subject(String.format("%s - Order confirmed", order.getRestaurant().getName()))
                .body(String.format("Order of code <strong> %s </strong> was confirmed!", order.getCode()))
                .recipient(order.getUser().getEmail())
                .build();

        emailService.send(message);
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
