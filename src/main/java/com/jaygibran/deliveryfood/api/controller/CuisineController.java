package com.jaygibran.deliveryfood.api.controller;


import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.repository.CuisineRepository;
import com.jaygibran.deliveryfood.domain.service.CuisineRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/cuisines")
public class CuisineController {

    private CuisineRegistryService cuisineRegistryService;

    private CuisineRepository cuisineRepository;

    @GetMapping
    public List<Cuisine> list() {
        return this.cuisineRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cuisine search(@PathVariable Long id) {
        return cuisineRegistryService.searchOrFail(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cuisine add(@RequestBody Cuisine cuisine) {
        return cuisineRegistryService.save(cuisine);
    }

    @PutMapping("/{id}")
    public Cuisine update(@PathVariable Long id, @RequestBody Cuisine cuisine) {
        Cuisine cuisineToUpdate = cuisineRegistryService.searchOrFail(id);

        BeanUtils.copyProperties(cuisine, cuisineToUpdate, "id");

        return this.cuisineRegistryService.save(cuisineToUpdate);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.cuisineRegistryService.delete(id);
    }
}
