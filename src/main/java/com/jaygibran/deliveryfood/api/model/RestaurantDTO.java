package com.jaygibran.deliveryfood.api.model;

import com.jaygibran.deliveryfood.domain.model.Cuisine;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantDTO {

    private Long id;
    private String name;
    private BigDecimal deliveryFee;
    private CuisineDTO cuisine;
}
