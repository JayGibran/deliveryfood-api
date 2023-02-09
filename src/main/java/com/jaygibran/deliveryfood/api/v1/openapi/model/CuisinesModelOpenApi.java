package com.jaygibran.deliveryfood.api.v1.openapi.model;

import com.jaygibran.deliveryfood.api.v1.model.CuisineDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CuisinesDTO")
@Getter
@Setter
public class CuisinesModelOpenApi extends PagedModelOpenApi<CuisineDTO> {
}
