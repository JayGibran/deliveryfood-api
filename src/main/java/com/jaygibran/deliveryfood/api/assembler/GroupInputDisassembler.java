package com.jaygibran.deliveryfood.api.assembler;

import com.jaygibran.deliveryfood.api.model.input.GroupInput;
import com.jaygibran.deliveryfood.domain.model.Group;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class GroupInputDisassembler {

    private final ModelMapper modelMapper;

    public Group toDomain(GroupInput groupInput) {
        return modelMapper.map(groupInput, Group.class);
    }

    public void copyToDomainObject(GroupInput groupInput, Group groupToUpdate) {
        modelMapper.map(groupInput, groupToUpdate);
    }
}