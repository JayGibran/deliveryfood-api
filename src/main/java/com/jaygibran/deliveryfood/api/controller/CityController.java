package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.City;
import com.jaygibran.deliveryfood.domain.repository.CityRepository;
import com.jaygibran.deliveryfood.domain.service.CityRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<City> search(@PathVariable Long id) {
        Optional<City> city = cityRepository.findById(id);
        if (city.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(city.get());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody City city) {
        try {
            City savedCity = cityRegistryService.save(city);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCity);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody City city) {
        Optional<City> cityToUpdate = this.cityRepository.findById(id);
        if (cityToUpdate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        try {
            BeanUtils.copyProperties(city, cityToUpdate.get(), "id");
            City updatedCity = this.cityRegistryService.save(cityToUpdate.get());
            return ResponseEntity.ok(updatedCity);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<City> delete(@PathVariable Long id) {
        try {
            this.cityRegistryService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
