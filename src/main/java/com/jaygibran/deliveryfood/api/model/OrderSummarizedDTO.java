package com.jaygibran.deliveryfood.api.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class OrderSummarizedDTO {

    @ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private String code;

    @ApiModelProperty(example = "298.90")
    private BigDecimal subTotal;

    @ApiModelProperty(example = "10.00")
    private BigDecimal total;

    @ApiModelProperty(example = "308.90")
    private BigDecimal feeDelivery;

    @ApiModelProperty(example = "CREATED")
    private String status;

    @ApiModelProperty(example = "2019-12-01T20:34:04Z")
    private OffsetDateTime dateCreated;

    private RestaurantSummarizedDTO restaurant;

    private UserDTO user;
}
