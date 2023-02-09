package com.jaygibran.deliveryfood.api.v1.openapi.model;

import com.jaygibran.deliveryfood.api.v1.model.CuisineDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@ApiModel("RestaurantBasicModel")
@Getter
@Setter
public class RestaurantBasicModelOpenApi {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Thai Gourmet")
    private String name;

    @ApiModelProperty(example = "10.00")
    private BigDecimal deliveryFee;

    private CuisineDTO cuisine;
}
