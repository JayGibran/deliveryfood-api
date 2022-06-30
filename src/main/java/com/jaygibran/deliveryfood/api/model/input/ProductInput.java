package com.jaygibran.deliveryfood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductInput {

    @NotBlank
    private String name;

    @PositiveOrZero
    @NotNull
    private BigDecimal price;

    @NotBlank
    private String description;

    @NotNull
    private Boolean active;
}
