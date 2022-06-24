package com.jaygibran.deliveryfood.api.assembler;

import com.jaygibran.deliveryfood.api.model.input.CuisineInput;
import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.model.State;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CuisineInputDisassembler {

    private final ModelMapper modelMapper;

    public Cuisine toDomain(CuisineInput cuisineInput) {
        return this.modelMapper.map(cuisineInput, Cuisine.class);
    }

    public void copyToDomainObject(CuisineInput stateInput, Cuisine cuisine) {
        modelMapper.map(stateInput, cuisine);
    }

}
