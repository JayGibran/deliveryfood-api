package com.jaygibran.deliveryfood.api.assembler;

import com.jaygibran.deliveryfood.api.model.RestaurantDTO;
import com.jaygibran.deliveryfood.api.model.StateDTO;
import com.jaygibran.deliveryfood.domain.model.Restaurant;
import com.jaygibran.deliveryfood.domain.model.State;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class StateDTOAssembler {

    private final ModelMapper modelMapper;

    public StateDTO toDTO(State state) {
        return modelMapper.map(state, StateDTO.class);
    }

    public List<StateDTO> toCollectionDTO(List<State> states) {
        return states.stream().map(state -> toDTO(state))
                .collect(Collectors.toList());
    }
}
