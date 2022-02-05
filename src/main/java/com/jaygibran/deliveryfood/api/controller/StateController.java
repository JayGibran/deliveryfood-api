package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.City;
import com.jaygibran.deliveryfood.domain.model.State;
import com.jaygibran.deliveryfood.domain.repository.StateRepository;
import com.jaygibran.deliveryfood.domain.service.StateRegistryService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/states")
public class StateController {

    private StateRepository stateRepository;

    private StateRegistryService stateRegistryService;

    @GetMapping
    public List<State> list(){
        return this.stateRepository.all();
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> search(@PathVariable Long id){
        State state = this.stateRepository.findById(id);
        if(state == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(state);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public State save(@RequestBody State state){
        return stateRegistryService.save(state);
    }

    @PutMapping("/{id}")
    public ResponseEntity<State> update(@PathVariable Long id, @RequestBody State state){
        State stateToUpdate = this.stateRepository.findById(id);
        if(stateToUpdate == null){
            return ResponseEntity.notFound().build();
        }
        BeanUtils.copyProperties(state, stateToUpdate, "id");
        State updatedState = stateRegistryService.save(stateToUpdate);
        return ResponseEntity.ok(updatedState);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            stateRegistryService.delete(id);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (EntityInUseException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
