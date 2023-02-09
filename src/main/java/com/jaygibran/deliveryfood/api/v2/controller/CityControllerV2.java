package com.jaygibran.deliveryfood.api.v2.controller;

import com.jaygibran.deliveryfood.api.ResourceUriHelper;
import com.jaygibran.deliveryfood.api.v2.assembler.CityDTOAssemblerV2;
import com.jaygibran.deliveryfood.api.v2.assembler.CityInputDisassemblerV2;
import com.jaygibran.deliveryfood.api.v2.model.CityDTOV2;
import com.jaygibran.deliveryfood.api.v2.model.input.CityInputV2;
import com.jaygibran.deliveryfood.domain.exception.BusinessException;
import com.jaygibran.deliveryfood.domain.exception.StateNotFoundException;
import com.jaygibran.deliveryfood.domain.model.City;
import com.jaygibran.deliveryfood.domain.repository.CityRepository;
import com.jaygibran.deliveryfood.domain.service.CityRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(path = "/v2/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityControllerV2 {

    private CityRepository cityRepository;

    private CityRegistryService cityRegistryService;

    private CityDTOAssemblerV2 cityDTOAssembler;

    private CityInputDisassemblerV2 cityInputDisassembler;

    @GetMapping
    public CollectionModel<CityDTOV2> list() {
        return cityDTOAssembler.toCollectionModel(cityRepository.findAll());
    }

    @GetMapping(path = "/{id}")
    public CityDTOV2 search(@PathVariable Long id) {
        return cityDTOAssembler.toModel(cityRegistryService.findOrFail(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CityDTOV2 save(@RequestBody @Valid CityInputV2 cityInput) {
        try {
            City city = cityInputDisassembler.toDomain(cityInput);

            CityDTOV2 cityDTO = cityDTOAssembler.toModel(cityRegistryService.save(city));

            ResourceUriHelper.addUriInResponseHeader(cityDTO.getIdCity());

            return cityDTO;
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping(path = "/{id}")
    public CityDTOV2 update(@PathVariable Long id,
                            @RequestBody @Valid CityInputV2 cityInput) {
        try {
            City cityToUpdate = this.cityRegistryService.findOrFail(id);

            cityInputDisassembler.copyToDomainObject(cityInput, cityToUpdate);

            return cityDTOAssembler.toModel(this.cityRegistryService.save(cityToUpdate));
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

}
