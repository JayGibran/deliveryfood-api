package com.jaygibran.deliveryfood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressInput {

    @ApiModelProperty(example = "D02 PP56", required = true)
    @NotBlank
    private String airCode;

    @ApiModelProperty(example = "Pearse Street", required = true)
    @NotBlank
    private String street;

    @ApiModelProperty(example = "1500", required = true)
    @NotBlank
    private String number;

    @ApiModelProperty(example = "Apto 901")
    private String additional;

    @ApiModelProperty(example = "City Center")
    @NotBlank
    private String neighborhood;

    @Valid
    @NotNull
    private CityIdInput city;
}
