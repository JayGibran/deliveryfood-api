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
public class CuisinesModelOpenApi extends PagedModelOpenApi<CuisineDTO> {
}
