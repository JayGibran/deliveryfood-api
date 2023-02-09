package com.jaygibran.deliveryfood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityResumeDTO {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Dublin")
    private String name;

    @ApiModelProperty(example = "Leinster")
    private String state;
}
