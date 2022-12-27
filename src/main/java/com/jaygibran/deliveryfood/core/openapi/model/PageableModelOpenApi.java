package com.jaygibran.deliveryfood.core.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("Pageable")
@Setter
@Getter
public class PageableModelOpenApi {

    @ApiModelProperty(example = "0", value = "Page number starts in 0")
    private int page;

    @ApiModelProperty(example = "10", value = "Quantity of elements by page")
    private int size;

    @ApiModelProperty(example = "name,asc", value = "Property name to sort")
    private List<String> sort;
}
