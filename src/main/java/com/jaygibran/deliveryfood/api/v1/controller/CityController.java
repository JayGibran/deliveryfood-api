package com.jaygibran.deliveryfood.api.v1.controller;

import com.jaygibran.deliveryfood.api.ResourceUriHelper;
import com.jaygibran.deliveryfood.api.exceptionhandler.Problem;
import com.jaygibran.deliveryfood.api.v1.assembler.CityDTOAssembler;
import com.jaygibran.deliveryfood.api.v1.assembler.CityInputDisassembler;
import com.jaygibran.deliveryfood.api.v1.model.CityDTO;
import com.jaygibran.deliveryfood.api.v1.model.input.CityInput;
import com.jaygibran.deliveryfood.api.v1.openapi.controller.CityControllerOpenApi;
import com.jaygibran.deliveryfood.core.web.DeliveryFoodMediaTypes;
import com.jaygibran.deliveryfood.domain.exception.BusinessException;
import com.jaygibran.deliveryfood.domain.exception.StateNotFoundException;
import com.jaygibran.deliveryfood.domain.model.City;
import com.jaygibran.deliveryfood.domain.repository.CityRepository;
import com.jaygibran.deliveryfood.domain.service.CityRegistryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/cities")
public class CityController implements CityControllerOpenApi {

    private CityRepository cityRepository;

    private CityRegistryService cityRegistryService;

    private CityDTOAssembler cityDTOAssembler;

    private CityInputDisassembler cityInputDisassembler;

    @GetMapping(produces = DeliveryFoodMediaTypes.V1_APPLICATION_JSON_VALUE)
    public CollectionModel<CityDTO> list() {
        return cityDTOAssembler.toCollectionModel(cityRepository.findAll());
    }

    @GetMapping(path = "/{id}", produces = DeliveryFoodMediaTypes.V1_APPLICATION_JSON_VALUE)
    public CityDTO search(@PathVariable Long id) {
        return cityDTOAssembler.toModel(cityRegistryService.findOrFail(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = DeliveryFoodMediaTypes.V1_APPLICATION_JSON_VALUE)
    public CityDTO save(@RequestBody @Valid CityInput cityInput) {
        try {
            City city = cityInputDisassembler.toDomain(cityInput);

            CityDTO cityDTO = cityDTOAssembler.toModel(cityRegistryService.save(city));

            ResourceUriHelper.addUriInResponseHeader(cityDTO.getId());

            return cityDTO;
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping(path = "/{id}", produces = DeliveryFoodMediaTypes.V1_APPLICATION_JSON_VALUE)
    public CityDTO update(@PathVariable Long id,
                          @RequestBody @Valid CityInput cityInput) {
        try {
            City cityToUpdate = this.cityRegistryService.findOrFail(id);

            cityInputDisassembler.copyToDomainObject(cityInput, cityToUpdate);

            return cityDTOAssembler.toModel(this.cityRegistryService.save(cityToUpdate));
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @ApiOperation("Delete city by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "City was deleted"),
            @ApiResponse(responseCode = "404", description = "City was not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@ApiParam(value = "City id", example = "1") @PathVariable Long id) {
        this.cityRegistryService.delete(id);
    }
}
