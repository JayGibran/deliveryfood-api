package com.jaygibran.deliveryfood.api.v1.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.jaygibran.deliveryfood.api.v1.model.view.RestaurantView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantDTO {

    @JsonView({RestaurantView.Summary.class, RestaurantView.OnlyNames.class})
    private Long id;

    @JsonView({RestaurantView.Summary.class, RestaurantView.OnlyNames.class})
    private String name;

    @JsonView(RestaurantView.Summary.class)
    private BigDecimal deliveryFee;

    @JsonView(RestaurantView.Summary.class)
    private CuisineDTO cuisine;

    private Boolean active;
    private Boolean open;
    private AddressDTO address;
}
