package com.jaygibran.deliveryfood.api.v1.openapi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedModelOpenApi<T> {

    private List<T> content;

    @ApiModelProperty(example = "10", value = "Quantity of items by page")
    private Long size;

    @ApiModelProperty(example = "50", value = "Total of items")
    private Long totalElements;

    @ApiModelProperty(example = "5", value = "Total of pages")
    private Long totalPages;

    @ApiModelProperty(example = "0", value = "Page number (starts at 0)")
    private Long number;
}
