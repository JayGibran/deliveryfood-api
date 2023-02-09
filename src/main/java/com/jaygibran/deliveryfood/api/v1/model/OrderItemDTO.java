package com.jaygibran.deliveryfood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemDTO {

    @ApiModelProperty(example = "1")
    private Long productId;

    @ApiModelProperty(example = "Rib-eye Steak")
    private String productName;

    @ApiModelProperty(example = "1")
    private Integer quantity;

    @ApiModelProperty(example = "35.00")
    private BigDecimal unitPrice;

    @ApiModelProperty(example = "35.00")
    private BigDecimal total;

    @ApiModelProperty(example = "Medium-cooked steak, please")
    private String note;
}
