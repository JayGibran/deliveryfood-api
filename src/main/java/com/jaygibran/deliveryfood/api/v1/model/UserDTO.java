package com.jaygibran.deliveryfood.api.v1.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "users")
@Getter
@Setter
public class UserDTO extends RepresentationModel<UserDTO> {

    private Long id;

    private String name;

    private String email;
}
