package com.jaygibran.deliveryfood.api.v1.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTO {

    private Long id;

    private String name;

    private BigDecimal price;

    private String description;

    private Boolean active;
}
