package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.model.Order;
import com.jaygibran.deliveryfood.domain.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class FlowOrderService {

    private final OrderRegistryService orderRegistryService;
    private final OrderRepository orderRepository;

    @Transactional
    public void confirm(String orderCode) {
        Order order = orderRegistryService.findOrFail(orderCode);
        order.confirm();
        
        orderRepository.save(order);
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

        orderRepository.save(order);
    }
}
