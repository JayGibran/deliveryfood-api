package com.jaygibran.deliveryfood.api.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.jaygibran.deliveryfood.api.assembler.OrderDTOAssembler;
import com.jaygibran.deliveryfood.api.assembler.OrderSummarizedDTOAssembler;
import com.jaygibran.deliveryfood.api.model.CuisineDTO;
import com.jaygibran.deliveryfood.api.model.OrderDTO;
import com.jaygibran.deliveryfood.api.model.OrderSummarizedDTO;
import com.jaygibran.deliveryfood.api.model.input.OrderInput;
import com.jaygibran.deliveryfood.api.model.input.OrderInputDisassembler;
import com.jaygibran.deliveryfood.domain.exception.BusinessException;
import com.jaygibran.deliveryfood.domain.exception.CityNotFoundException;
import com.jaygibran.deliveryfood.domain.exception.PaymentMethodNotFoundException;
import com.jaygibran.deliveryfood.domain.exception.ProductNotFoundException;
import com.jaygibran.deliveryfood.domain.exception.RestaurantNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Order;
import com.jaygibran.deliveryfood.domain.model.User;
import com.jaygibran.deliveryfood.domain.repository.OrderRepository;
import com.jaygibran.deliveryfood.domain.repository.filter.OrderFilter;
import com.jaygibran.deliveryfood.domain.service.OrderRegistryService;
import com.jaygibran.deliveryfood.infrastructure.repository.spec.OrderSpecs;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderRegistryService orderRegistryService;
    private final OrderDTOAssembler orderDTOAssembler;
    private final OrderSummarizedDTOAssembler orderSummarizedDTOAssembler;
    private final OrderInputDisassembler orderInputDisassembler;

    @GetMapping
    public Page<OrderSummarizedDTO> search(OrderFilter orderFilter, Pageable pageable) {
        Page<Order> orderPages = orderRepository.findAll(OrderSpecs.usingFilter(orderFilter), pageable);

        List<OrderSummarizedDTO> orderSummarizedDTOS = orderSummarizedDTOAssembler.toCollectionDTO(orderPages.getContent());

        Page<OrderSummarizedDTO> cuisineDTOPage = new PageImpl<>(orderSummarizedDTOS, pageable, orderPages.getTotalElements());

        return cuisineDTOPage;
    }

    @GetMapping("/{orderCode}")
    public OrderDTO search(@PathVariable String orderCode) {
        return orderDTOAssembler.toDTO(orderRegistryService.findOrFail(orderCode));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderDTO save(@RequestBody @Valid OrderInput orderInput) {
        try {
            Order order = orderInputDisassembler.toDomain(orderInput);
            order.setUser(User.builder().id(1L).build());

            return orderDTOAssembler.toDTO(orderRegistryService.placeOrder(order));
        } catch (RestaurantNotFoundException | PaymentMethodNotFoundException | CityNotFoundException | ProductNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
