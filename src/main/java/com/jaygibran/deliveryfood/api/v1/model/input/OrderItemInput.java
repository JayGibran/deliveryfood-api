package com.jaygibran.deliveryfood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class OrderItemInput {

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long productId;

    @ApiModelProperty(example = "2", required = true)
    @Positive
    @NotNull
    private Integer quantity;

    @ApiModelProperty(example = "Less hot, please", required = true)
    private String note;
}
