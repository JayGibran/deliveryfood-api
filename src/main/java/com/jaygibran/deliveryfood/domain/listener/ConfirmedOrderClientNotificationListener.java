package com.jaygibran.deliveryfood.domain.listener;

import com.jaygibran.deliveryfood.domain.event.OrderConfirmedEvent;
import com.jaygibran.deliveryfood.domain.model.Order;
import com.jaygibran.deliveryfood.domain.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class ConfirmedOrderClientNotificationListener {
    
    private final EmailService emailService;
    
    @TransactionalEventListener
    public void whenConfirmOrder(OrderConfirmedEvent orderConfirmedEvent){
        Order order = orderConfirmedEvent.getOrder();
        
        EmailService.Message message = EmailService.Message.builder()
                .subject(String.format("%s - Order confirmed", order.getRestaurant().getName()))
                .body("order-confirmed.html")
                .variable("order", order)
                .recipient(order.getUser().getEmail())
                .build();

        emailService.send(message);
    }
}
