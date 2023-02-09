package com.jaygibran.deliveryfood.api.v1.assembler;

import com.jaygibran.deliveryfood.api.v1.model.OrderDTO;
import com.jaygibran.deliveryfood.domain.model.Order;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
