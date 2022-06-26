package com.jaygibran.deliveryfood.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserDTO {

    private String name;

    private String email;
}
