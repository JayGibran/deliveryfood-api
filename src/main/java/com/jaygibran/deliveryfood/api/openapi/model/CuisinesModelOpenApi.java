package com.jaygibran.deliveryfood.api.openapi.model;

import com.jaygibran.deliveryfood.api.model.CuisineDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("CuisinesDTO")
@Getter
@Setter
public class CuisinesModelOpenApi {

    private List<CuisineDTO> content;

    @ApiModelProperty(example = "10", value = "Quantity of items by page")
    private Long size;

    @ApiModelProperty(example = "50", value = "Total of items")
    private Long totalElements;

    @ApiModelProperty(example = "5", value = "Total of pages")
    private Long totalPages;

    @ApiModelProperty(example = "0", value = "Page number (starts at 0)")
    private Long number;
}
