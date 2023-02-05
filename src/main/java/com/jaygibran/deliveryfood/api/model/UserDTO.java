package com.jaygibran.deliveryfood.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Relation(collectionRelation = "users")
@Getter
@Setter
public class UserDTO extends RepresentationModel<UserDTO> {

    private Long id;

    private String name;

    private String email;
}
