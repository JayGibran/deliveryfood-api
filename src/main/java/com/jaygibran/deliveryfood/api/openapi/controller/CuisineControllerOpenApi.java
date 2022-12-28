package com.jaygibran.deliveryfood.api.openapi.controller;

import com.jaygibran.deliveryfood.api.exceptionhandler.Problem;
import com.jaygibran.deliveryfood.api.model.CuisineDTO;
import com.jaygibran.deliveryfood.api.model.input.CuisineInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Cuisines")
public interface CuisineControllerOpenApi {

    @ApiOperation("List cuisines")
    Page<CuisineDTO> list(Pageable pageable);

    @ApiOperation(value = "Search cuisine by id", response = CuisineDTO.class)
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid Cuisine Id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Cuisines does not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CuisineDTO search(@ApiParam(value = "Cuisine id", example = "1") Long id);

    @ApiOperation("Registry cuisine")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cuisine was registered"),
    })
    CuisineDTO add(@ApiParam(name = "body", value = "Representation of a new cuisine") CuisineInput cuisineInput);

    @ApiOperation("Update cuisine by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuisine was updated"),
            @ApiResponse(responseCode = "404", description = "Cuisine was not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
    })
    CuisineDTO update(@ApiParam(value = "Cuisine id", example = "1") Long id,
                      @ApiParam(name = "body", value = "Representation of a cuisine with new data") CuisineInput cuisineInput);

    @ApiOperation("Delete cuisine by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuisine was deleted"),
            @ApiResponse(responseCode = "404", description = "Cuisine was not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
    })
    void delete(@ApiParam(value = "Cuisine id", example = "1") Long id);
}
