package com.jaygibran.deliveryfood.api.v1.controller;

import com.jaygibran.deliveryfood.api.v1.assembler.OrderDTOAssembler;
import com.jaygibran.deliveryfood.api.v1.assembler.OrderSummarizedDTOAssembler;
import com.jaygibran.deliveryfood.api.v1.model.OrderDTO;
import com.jaygibran.deliveryfood.api.v1.model.OrderSummarizedDTO;
import com.jaygibran.deliveryfood.api.v1.model.input.OrderInput;
import com.jaygibran.deliveryfood.api.v1.model.input.OrderInputDisassembler;
import com.jaygibran.deliveryfood.api.v1.openapi.controller.OrderControllerOpenApi;
import com.jaygibran.deliveryfood.core.data.PageableTranslator;
import com.jaygibran.deliveryfood.domain.exception.BusinessException;
import com.jaygibran.deliveryfood.domain.exception.CityNotFoundException;
import com.jaygibran.deliveryfood.domain.exception.PaymentMethodNotFoundException;
import com.jaygibran.deliveryfood.domain.exception.ProductNotFoundException;
import com.jaygibran.deliveryfood.domain.exception.RestaurantNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Order;
import com.jaygibran.deliveryfood.domain.model.User;
import com.jaygibran.deliveryfood.domain.repository.OrderRepository;
import com.jaygibran.deliveryfood.domain.filter.OrderFilter;
import com.jaygibran.deliveryfood.domain.service.OrderRegistryService;
import com.jaygibran.deliveryfood.infrastructure.repository.spec.OrderSpecs;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController implements OrderControllerOpenApi {

    private final OrderRepository orderRepository;
    private final OrderRegistryService orderRegistryService;
    private final OrderDTOAssembler orderDTOAssembler;
    private final OrderSummarizedDTOAssembler orderSummarizedDTOAssembler;
    private final OrderInputDisassembler orderInputDisassembler;

    @GetMapping
    public Page<OrderSummarizedDTO> search(OrderFilter orderFilter, Pageable pageable) {
        pageable = translatePageable(pageable);

        Page<Order> orderPages = orderRepository.findAll(OrderSpecs.usingFilter(orderFilter), pageable);

        List<OrderSummarizedDTO> orderSummarizedDTOS = orderSummarizedDTOAssembler.toCollectionDTO(orderPages.getContent());

        return new PageImpl<>(orderSummarizedDTOS, pageable, orderPages.getTotalElements());
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
        } catch (RestaurantNotFoundException | PaymentMethodNotFoundException | CityNotFoundException |
                 ProductNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    private Pageable translatePageable(Pageable apiPageable) {
        var map = Map.of(
                "code", "code",
                "user.name", "user.name",
                "restaurant.name", "restaurant.name",
                "total", "total"
        );

        return PageableTranslator.translate(apiPageable, map);
    }
}
