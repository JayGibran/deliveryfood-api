package com.jaygibran.deliveryfood.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.jaygibran.deliveryfood.api.model.view.RestaurantView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuisineDTO {

    @ApiModelProperty(example = "1")
    @JsonView(RestaurantView.Summary.class)
    private Long id;

    @ApiModelProperty(example = "Brazil")
    @JsonView(RestaurantView.Summary.class)
    private String name;
}
