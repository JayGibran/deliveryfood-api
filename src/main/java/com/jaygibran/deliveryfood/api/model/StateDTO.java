package com.jaygibran.deliveryfood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class StateDTO extends RepresentationModel<StateDTO> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Leinster")
    private String name;
}
