package com.jaygibran.deliveryfood.api.v1.assembler;

import com.jaygibran.deliveryfood.api.v1.controller.UserController;
import com.jaygibran.deliveryfood.api.v1.controller.UserGroupController;
import com.jaygibran.deliveryfood.api.v1.model.UserDTO;
import com.jaygibran.deliveryfood.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserDTOAssembler extends RepresentationModelAssemblerSupport<User, UserDTO> {

    private final ModelMapper modelMapper;

    public UserDTOAssembler(ModelMapper modelMapper) {
        super(UserController.class, UserDTO.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO toModel(User user) {
        UserDTO userDTO = createModelWithId(user.getId(), user);
        
        modelMapper.map(user, userDTO);
        
        userDTO.add(linkTo(UserController.class).withRel("users"));

        userDTO.add(linkTo(methodOn(UserGroupController.class).list(user.getId())).withRel("user-groups"));
        
        return userDTO;
    }
    
    @Override
    public CollectionModel<UserDTO> toCollectionModel(Iterable<? extends User> users) {
        return super.toCollectionModel(users).add(linkTo(UserController.class).withSelfRel());
    }
}
