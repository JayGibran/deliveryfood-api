package com.jaygibran.deliveryfood.api.openapi.controller;

import com.jaygibran.deliveryfood.api.exceptionhandler.Problem;
import com.jaygibran.deliveryfood.api.model.CityDTO;
import com.jaygibran.deliveryfood.api.model.input.CityInput;
import com.jaygibran.deliveryfood.domain.exception.BusinessException;
import com.jaygibran.deliveryfood.domain.exception.StateNotFoundException;
import com.jaygibran.deliveryfood.domain.model.City;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cities")
public interface CityControllerOpenApi {

    @ApiOperation("List cities")
    List<CityDTO> list();

    @ApiOperation(value = "Search city by id", response = City.class)
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid City Id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "City does not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CityDTO search(@ApiParam(value = "City id", example = "1") Long id);

    @ApiOperation("Registry city")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "City was registered"),
    })
    CityDTO save(@ApiParam(name = "body", value = "Representation of a new city") CityInput cityInput);

    @ApiOperation("Update city by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "City was updated"),
            @ApiResponse(responseCode = "404", description = "City was not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
    })
    CityDTO update(@ApiParam(value = "City id", example = "1") Long id,
                   @ApiParam(name = "body", value = "Representation of a city with new data") CityInput cityInput);

    @ApiOperation("Delete city by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "City was deleted"),
            @ApiResponse(responseCode = "404", description = "City was not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
    })
    void delete(@ApiParam(value = "City id", example = "1") Long id);
}
