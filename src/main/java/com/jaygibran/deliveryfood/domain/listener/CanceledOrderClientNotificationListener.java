package com.jaygibran.deliveryfood.domain.listener;

import com.jaygibran.deliveryfood.domain.event.OrderCanceledEvent;
import com.jaygibran.deliveryfood.domain.model.Order;
import com.jaygibran.deliveryfood.domain.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class CanceledOrderClientNotificationListener {

    private final EmailService emailService;

    @TransactionalEventListener
    public void whenCanceledOrder(OrderCanceledEvent orderCanceledEvent) {
        Order order = orderCanceledEvent.getOrder();

        EmailService.Message message = EmailService.Message.builder()
                .subject(String.format("%s - Order canceled", order.getRestaurant().getName()))
                .body("order-canceled.html")
                .variable("order", order)
                .recipient(order.getUser().getEmail())
                .build();

        emailService.send(message);
    }
}
