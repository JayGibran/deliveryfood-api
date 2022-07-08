package com.jaygibran.deliveryfood.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.jaygibran.deliveryfood.api.model.view.RestaurantView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuisineDTO {

    @JsonView(RestaurantView.Summary.class)
    private Long id;

    @JsonView(RestaurantView.Summary.class)
    private String name;
}
