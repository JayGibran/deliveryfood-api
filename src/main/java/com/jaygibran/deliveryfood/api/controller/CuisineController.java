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
    public ResponseEntity<Cuisine> search(@PathVariable Long id) {
        Optional<Cuisine> cuisine = this.cuisineRepository.findById(id);
        if (cuisine.isPresent()) {
            return ResponseEntity.ok(cuisine.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cuisine add(@RequestBody Cuisine cuisine) {
        return cuisineRegistryService.save(cuisine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuisine> update(@PathVariable Long id, @RequestBody Cuisine cuisine) {
        Optional<Cuisine> cuisineToUpdate = this.cuisineRepository.findById(id);

        if (cuisineToUpdate.isPresent()) {
            BeanUtils.copyProperties(cuisine, cuisineToUpdate.get(), "id");

            Cuisine updatedCuisine = this.cuisineRegistryService.save(cuisineToUpdate.get());
            return ResponseEntity.ok(updatedCuisine);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            this.cuisineRegistryService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (EntityInUseException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
