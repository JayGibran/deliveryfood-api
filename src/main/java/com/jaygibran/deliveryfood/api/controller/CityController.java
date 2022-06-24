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

@AllArgsConstructor
@RestController
@RequestMapping("/cities")
public class CityController {

    private CityRepository cityRepository;

    private CityRegistryService cityRegistryService;

    private CityDTOAssembler cityDTOAssembler;

    private CityInputDisassembler cityInputDisassembler;

    @GetMapping
    public List<CityDTO> list() {
        return cityDTOAssembler.toCollectionDTO(cityRepository.findAll());
    }

    @GetMapping("/{id}")
    public CityDTO search(@PathVariable Long id) {
        return cityDTOAssembler.toDTO(cityRegistryService.findOrFail(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CityDTO save(@RequestBody @Valid CityInput cityInput) {
        try {
            City city = cityInputDisassembler.toDomain(cityInput);

            return cityDTOAssembler.toDTO(cityRegistryService.save(city));
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public CityDTO update(@PathVariable Long id, @RequestBody @Valid CityInput cityInput) {
        try {
            City cityToUpdate = this.cityRegistryService.findOrFail(id);

            cityInputDisassembler.copyToDomainObject(cityInput, cityToUpdate);

            return cityDTOAssembler.toDTO(this.cityRegistryService.save(cityToUpdate));
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        this.cityRegistryService.delete(id);
    }
}
