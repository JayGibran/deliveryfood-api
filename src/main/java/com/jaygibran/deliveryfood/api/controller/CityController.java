package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.api.assembler.CityDTOAssembler;
import com.jaygibran.deliveryfood.api.assembler.CityInputDisassembler;
import com.jaygibran.deliveryfood.api.model.CityDTO;
import com.jaygibran.deliveryfood.api.model.input.CityInput;
import com.jaygibran.deliveryfood.domain.exception.BusinessException;
import com.jaygibran.deliveryfood.domain.exception.StateNotFoundException;
import com.jaygibran.deliveryfood.domain.model.City;
import com.jaygibran.deliveryfood.domain.repository.CityRepository;
import com.jaygibran.deliveryfood.domain.service.CityRegistryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
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
import java.util.List;

@Api(tags = "Cities")
@AllArgsConstructor
@RestController
@RequestMapping("/cities")
public class CityController {

    private CityRepository cityRepository;

    private CityRegistryService cityRegistryService;

    private CityDTOAssembler cityDTOAssembler;

    private CityInputDisassembler cityInputDisassembler;

    @ApiOperation("List cities")
    @GetMapping
    public List<CityDTO> list() {
        return cityDTOAssembler.toCollectionDTO(cityRepository.findAll());
    }

    @ApiOperation("Search city by id")
    @GetMapping("/{id}")
    public CityDTO search(@ApiParam(value = "City id", example = "1") @PathVariable Long id) {
        return cityDTOAssembler.toDTO(cityRegistryService.findOrFail(id));
    }

    @ApiOperation("Registry city")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CityDTO save(@ApiParam(name = "body", value = "Representation of a new city") @RequestBody @Valid CityInput cityInput) {
        try {
            City city = cityInputDisassembler.toDomain(cityInput);

            return cityDTOAssembler.toDTO(cityRegistryService.save(city));
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @ApiOperation("Update city by id")
    @PutMapping("/{id}")
    public CityDTO update(@ApiParam(value = "City id", example = "1") @PathVariable Long id,
                          @ApiParam(name = "body", value = "Representation of a city with new data") @RequestBody @Valid CityInput cityInput) {
        try {
            City cityToUpdate = this.cityRegistryService.findOrFail(id);

            cityInputDisassembler.copyToDomainObject(cityInput, cityToUpdate);

            return cityDTOAssembler.toDTO(this.cityRegistryService.save(cityToUpdate));
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @ApiOperation("Delete city by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@ApiParam(value = "City id", example = "1") @PathVariable Long id) {
        this.cityRegistryService.delete(id);
    }
}
