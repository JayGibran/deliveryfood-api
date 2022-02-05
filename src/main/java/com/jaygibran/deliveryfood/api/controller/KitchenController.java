package com.jaygibran.deliveryfood.api.controller;


import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Kitchen;
import com.jaygibran.deliveryfood.domain.repository.KitchenRepository;
import com.jaygibran.deliveryfood.domain.service.KitchenRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
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
@RequestMapping("/kitchens")
public class KitchenController {

    private KitchenRegistryService kitchenRegistryService;

    private KitchenRepository kitchenRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Kitchen> list(){
        return this.kitchenRepository.all();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kitchen> search(@PathVariable Long id){
        Kitchen kitchen = this.kitchenRepository.findById(id);
        if (kitchen != null){
            return ResponseEntity.ok(kitchen);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Kitchen add(@RequestBody Kitchen kitchen){
       return kitchenRegistryService.save(kitchen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kitchen> update(@PathVariable Long id, @RequestBody Kitchen kitchen){
        Kitchen kitchenToUpdate = this.kitchenRepository.findById(id);

        if(kitchenToUpdate != null){
            BeanUtils.copyProperties(kitchen, kitchenToUpdate, "id");

            Kitchen updatedKitchen = this.kitchenRegistryService.save(kitchenToUpdate);
            return ResponseEntity.ok(updatedKitchen);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Kitchen> delete(@PathVariable Long id){
        try{
            this.kitchenRegistryService.delete(id);
            return ResponseEntity.noContent().build();
        }catch(EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(EntityInUseException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
