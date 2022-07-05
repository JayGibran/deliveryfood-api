package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.api.assembler.OrderDTOAssembler;
import com.jaygibran.deliveryfood.api.model.OrderDTO;
import com.jaygibran.deliveryfood.domain.repository.OrderRepository;
import com.jaygibran.deliveryfood.domain.service.OrderRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderRegistryService orderRegistryService;
    private final OrderDTOAssembler orderDTOAssembler;

    @GetMapping
    public List<OrderDTO> list() {
        return orderDTOAssembler.toCollectionDTO(orderRepository.findAll());
    }

    @GetMapping("/{productId}")
    public OrderDTO search(@PathVariable Long productId) {
        return orderDTOAssembler.toDTO(orderRegistryService.findOrFail(productId));
    }
}
