package com.jaygibran.deliveryfood.api.assembler;

import com.jaygibran.deliveryfood.api.model.OrderDTO;
import com.jaygibran.deliveryfood.api.model.OrderSummarizedDTO;
import com.jaygibran.deliveryfood.domain.model.Order;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class OrderSummarizedDTOAssembler {

    private final ModelMapper modelMapper;

    public OrderSummarizedDTO toDTO(Order order) {
        return this.modelMapper.map(order, OrderSummarizedDTO.class);
    }

    public List<OrderSummarizedDTO> toCollectionDTO(List<Order> orders) {
        return orders
                .stream()
                .map(order -> toDTO(order)).collect(Collectors.toList());
    }
}
