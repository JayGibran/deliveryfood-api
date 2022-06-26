package com.jaygibran.deliveryfood.api.assembler;

import com.jaygibran.deliveryfood.api.model.RestaurantDTO;
import com.jaygibran.deliveryfood.api.model.input.GroupInput;
import com.jaygibran.deliveryfood.domain.model.Group;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class RestaurantDTOAssembler {

    private final ModelMapper modelMapper;

    public RestaurantDTO toDTO(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    public List<RestaurantDTO> toCollectionDTO(List<Restaurant> restaurants) {
        return restaurants.stream().map(restaurant -> toDTO(restaurant))
                .collect(Collectors.toList());
    }
}
