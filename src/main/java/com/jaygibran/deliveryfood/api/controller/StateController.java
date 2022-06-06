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

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/states")
public class StateController {

    private StateRepository stateRepository;

    private StateRegistryService stateRegistryService;

    @GetMapping
    public List<State> list() {
        return this.stateRepository.findAll();
    }

    @GetMapping("/{id}")
    public State search(@PathVariable Long id) {
        return this.stateRegistryService.findOrFail(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public State save(@RequestBody @Valid State state) {
        return stateRegistryService.save(state);
    }

    @PutMapping("/{id}")
    public State update(@PathVariable Long id, @RequestBody @Valid State state) {
        State stateToUpdate = this.stateRegistryService.findOrFail(id);

        BeanUtils.copyProperties(state, stateToUpdate, "id");

        return stateRegistryService.save(stateToUpdate);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        stateRegistryService.delete(id);
    }
}
