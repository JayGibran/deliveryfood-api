package com.jaygibran.deliveryfood.api.model;

import com.jaygibran.deliveryfood.domain.model.City;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class AddressDTO {

    @ApiModelProperty(example = "D02 PP56", required = true)
    private String airCode;

    @ApiModelProperty(example = "Pearse Street", required = true)
    private String street;

    @ApiModelProperty(example = "1500", required = true)
    private String number;

    @ApiModelProperty(example = "Apto 901")
    private String additional;

    @ApiModelProperty(example = "City Center")
    private String neighborhood;

    private CityResumeDTO city;
}
