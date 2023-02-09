package com.jaygibran.deliveryfood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CuisineInput {

    @ApiModelProperty(example = "Brazil", required = true)
    @NotBlank
    private String name;
}
