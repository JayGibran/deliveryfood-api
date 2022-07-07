package com.jaygibran.deliveryfood.api.model.input;

import com.jaygibran.deliveryfood.domain.model.Order;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OrderInputDisassembler {

    private final ModelMapper modelMapper;

    public Order toDomain(OrderInput orderInput) {
        return modelMapper.map(orderInput, Order.class);
    }
}
