package com.jaygibran.deliveryfood.api.controller;

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

    @GetMapping
    public List<City> list() {
        return cityRepository.findAll();
    }

    @GetMapping("/{id}")
    public City search(@PathVariable Long id) {
        return cityRegistryService.findOrFail(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public City save(@RequestBody @Valid City city) {
        try {
            return cityRegistryService.save(city);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public City update(@PathVariable Long id, @RequestBody @Valid City city) {
        try {
            City cityToUpdate = this.cityRegistryService.findOrFail(id);

            BeanUtils.copyProperties(city, cityToUpdate, "id");

            return this.cityRegistryService.save(cityToUpdate);
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
