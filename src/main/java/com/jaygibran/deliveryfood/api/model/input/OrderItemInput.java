package com.jaygibran.deliveryfood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class OrderItemInput {

    @NotNull
    private Long productId;

    @Positive
    @NotNull
    private Integer quantity;
    
    private String note;
}
