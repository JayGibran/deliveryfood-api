package com.jaygibran.deliveryfood.api.v1.assembler;

import com.jaygibran.deliveryfood.api.v1.model.input.StateInput;
import com.jaygibran.deliveryfood.domain.model.State;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class StateInputDisassembler {

    private final ModelMapper modelMapper;

    public State toDomain(StateInput stateInput) {
        return this.modelMapper.map(stateInput, State.class);
    }

    public void copyToDomainObject(StateInput stateInput, State state) {
        modelMapper.map(stateInput, state);
    }

}
