package com.jaygibran.deliveryfood.api.v1.assembler;

import com.jaygibran.deliveryfood.api.v1.model.input.CuisineInput;
import com.jaygibran.deliveryfood.domain.model.Cuisine;
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
