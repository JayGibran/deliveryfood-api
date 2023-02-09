package com.jaygibran.deliveryfood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDTO {

    @ApiModelProperty(example = "1")
    public Long id;

    @ApiModelProperty(example = "Manager")
    public String name;
}
