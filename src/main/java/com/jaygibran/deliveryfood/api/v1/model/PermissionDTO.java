package com.jaygibran.deliveryfood.api.v1.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class PermissionDTO {
    private Long id;

    private String name;

    private String description;
}
