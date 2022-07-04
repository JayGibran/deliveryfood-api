package com.jaygibran.deliveryfood.api.assembler;

import com.jaygibran.deliveryfood.api.model.UserDTO;
import com.jaygibran.deliveryfood.domain.model.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UserDTOAssembler {

    private final ModelMapper modelMapper;

    public UserDTO toDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public List<UserDTO> toCollectionDTO(Collection<User> users) {
        return users.stream().map(user -> toDTO(user))
                .collect(Collectors.toList());
    }
}
