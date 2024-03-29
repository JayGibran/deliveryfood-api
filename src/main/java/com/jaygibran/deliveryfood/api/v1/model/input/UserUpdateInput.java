package com.jaygibran.deliveryfood.api.v1.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserUpdateInput {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;
}
