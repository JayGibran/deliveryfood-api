package com.jaygibran.deliveryfood.api.v2.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityInputV2 {

    @ApiModelProperty(example = "Dublin", required = true)
    @NotBlank
    private String nameCity;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long idState;
}
