package com.jaygibran.deliveryfood.api.v1.openapi.controller;

import com.jaygibran.deliveryfood.api.exceptionhandler.Problem;
import com.jaygibran.deliveryfood.api.v1.model.OrderDTO;
import com.jaygibran.deliveryfood.api.v1.model.OrderSummarizedDTO;
import com.jaygibran.deliveryfood.api.v1.model.input.OrderInput;
import com.jaygibran.deliveryfood.domain.filter.OrderFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

@Api(tags = "Orders")
public interface OrderControllerOpenApi {

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Property name to filter on response, separated by comma",
                    name = "fields", paramType = "query", type = "string")
    })
    @ApiOperation("Search orders")
    @GetMapping
    Page<OrderSummarizedDTO> search(OrderFilter orderFilter, Pageable pageable);

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Property name to filter on response, separated by comma",
                    name = "fields", paramType = "query", type = "string")
    })
    @ApiOperation("Search a order by code")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Order does not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    OrderDTO search(@ApiParam(value = "Order code", example = "f9981ca4-5a5e-4da3-af04-933861df3e55") String orderCode);

    @ApiOperation("Registry order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order was registered"),
    })
    OrderDTO save(@ApiParam(name = "bodt", value = "Representation of a order with new data") OrderInput orderInput);
}
