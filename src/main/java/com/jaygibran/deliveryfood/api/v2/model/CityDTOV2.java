package com.jaygibran.deliveryfood.api.v2.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cities")
@Getter
@Setter
public class CityDTOV2 extends RepresentationModel<CityDTOV2> {

    @ApiModelProperty(example = "1")
    private Long idCity;

    @ApiModelProperty(example = "Dublin")
    private String nameCity;

    private Long idState;
    private String nameState;
}
