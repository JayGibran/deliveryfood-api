package com.jaygibran.deliveryfood.api.assembler;

import com.jaygibran.deliveryfood.api.model.CuisineDTO;
import com.jaygibran.deliveryfood.api.model.StateDTO;
import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.model.State;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CuisineDTOAssembler {

    private final ModelMapper modelMapper;

    public CuisineDTO toDTO(Cuisine cuisine) {
        return this.modelMapper.map(cuisine, CuisineDTO.class);
    }

    public List<CuisineDTO> toCollectionDTO(List<Cuisine> cuisines) {
        return cuisines.stream().map(cuisine -> toDTO(cuisine))
                .collect(Collectors.toList());
    }
}
