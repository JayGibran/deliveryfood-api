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

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cuisines")
public class CuisineController {

    private CuisineRegistryService cuisineRegistryService;

    private CuisineRepository cuisineRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cuisine> list(){
        return this.cuisineRepository.all();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuisine> search(@PathVariable Long id){
        Cuisine cuisine = this.cuisineRepository.findById(id);
        if (cuisine != null){
            return ResponseEntity.ok(cuisine);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cuisine add(@RequestBody Cuisine cuisine){
       return cuisineRegistryService.save(cuisine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuisine> update(@PathVariable Long id, @RequestBody Cuisine cuisine){
        Cuisine cuisineToUpdate = this.cuisineRepository.findById(id);

        if(cuisineToUpdate != null){
            BeanUtils.copyProperties(cuisine, cuisineToUpdate, "id");

            Cuisine updatedCuisine = this.cuisineRegistryService.save(cuisineToUpdate);
            return ResponseEntity.ok(updatedCuisine);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cuisine> delete(@PathVariable Long id){
        try{
            this.cuisineRegistryService.delete(id);
            return ResponseEntity.noContent().build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(EntityInUseException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
