package com.jaygibran.deliveryfood.api.openapi.controller;

import com.jaygibran.deliveryfood.api.exceptionhandler.Problem;
import com.jaygibran.deliveryfood.api.model.CuisineDTO;
import com.jaygibran.deliveryfood.api.model.PaymentMethodDTO;
import com.jaygibran.deliveryfood.api.model.input.CuisineInput;
import com.jaygibran.deliveryfood.api.model.input.PaymentMethodInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

@Api(tags = "Payment Methods")
public interface PaymentMethodControllerOpenApi {

    @ApiOperation("List payment methods")
    ResponseEntity<List<PaymentMethodDTO>> list();

    @ApiOperation(value = "Search payment method by id", response = PaymentMethodDTO.class)
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid Payment Method Id", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Payment Method does not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<PaymentMethodDTO> search(@ApiParam(value = "Payment Method id", example = "1") Long id, ServletWebRequest request);

    @ApiOperation("Registry payment method")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Payment method was registered"),
    })
    PaymentMethodDTO add(@ApiParam(name = "body", value = "Representation of a new payment method") PaymentMethodInput paymentMethodInput);

    @ApiOperation("Update payment method by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment Method was updated"),
            @ApiResponse(responseCode = "404", description = "Payment Method was not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
    })
    PaymentMethodDTO update(@ApiParam(value = "Payment method id", example = "1") Long id,
                            @ApiParam(name = "body", value = "Representation of a payment method with new data") PaymentMethodInput paymentMethodInput);

    @ApiOperation("Delete Payment method by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment method was deleted"),
            @ApiResponse(responseCode = "404", description = "Payment method was not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
    })
    void delete(@ApiParam(value = "Payment method id", example = "1") Long id);
}
