package com.jaygibran.deliveryfood.api.assembler;

import com.jaygibran.deliveryfood.api.model.input.GroupInput;
import com.jaygibran.deliveryfood.api.model.input.UserInput;
import com.jaygibran.deliveryfood.api.model.input.UserUpdateInput;
import com.jaygibran.deliveryfood.domain.model.Group;
import com.jaygibran.deliveryfood.domain.model.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserInputDisassembler {

    private final ModelMapper modelMapper;

    public User toDomain(UserInput userInput) {
        return modelMapper.map(userInput, User.class);
    }

    public void copyToDomainObject(UserUpdateInput userUpdateInput, User userToUpdate) {
        modelMapper.map(userUpdateInput, userToUpdate);
    }
}