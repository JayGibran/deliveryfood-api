package com.jaygibran.deliveryfood.api.openapi.controller;

import com.jaygibran.deliveryfood.api.exceptionhandler.Problem;
import com.jaygibran.deliveryfood.api.model.GroupDTO;
import com.jaygibran.deliveryfood.api.model.input.CityInput;
import com.jaygibran.deliveryfood.api.model.input.GroupInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@Api(tags = "Groups")
public interface GroupControllerOpenApi {

    @ApiOperation("List groups")
    List<GroupDTO> list();

    @ApiOperation(value = "Search group by id", response = GroupDTO.class)
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid Group Id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Groups does not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    GroupDTO search(@ApiParam(value = "Group id", example = "1") Long id);

    @ApiOperation("Registry group")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Group was registered"),
    })
    GroupDTO add(@ApiParam(name = "body", value = "Representation of a new group") GroupInput groupInput);

    @ApiOperation("Update group by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Group was updated"),
            @ApiResponse(responseCode = "404", description = "Group was not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
    })
    GroupDTO update(@ApiParam(value = "Group id", example = "1") Long id,
                    @ApiParam(name = "body", value = "Representation of a group with new data") GroupInput groupInput);

    @ApiOperation("Delete group by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Group was deleted"),
            @ApiResponse(responseCode = "404", description = "Group was not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
    })
    void delete(@ApiParam(value = "Group id", example = "1") Long id);
}
