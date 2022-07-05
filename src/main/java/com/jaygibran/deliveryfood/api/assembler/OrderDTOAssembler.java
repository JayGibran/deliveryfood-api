package com.jaygibran.deliveryfood.api.assembler;

import com.jaygibran.deliveryfood.api.model.OrderDTO;
import com.jaygibran.deliveryfood.api.model.PaymentMethodDTO;
import com.jaygibran.deliveryfood.domain.model.Order;
import com.jaygibran.deliveryfood.domain.model.PaymentMethod;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class OrderDTOAssembler {

    private final ModelMapper modelMapper;

    public OrderDTO toDTO(Order order) {
        return this.modelMapper.map(order, OrderDTO.class);
    }

    public List<OrderDTO> toCollectionDTO(List<Order> orders) {
        return orders
                .stream()
                .map(order -> toDTO(order)).collect(Collectors.toList());
    }
}
