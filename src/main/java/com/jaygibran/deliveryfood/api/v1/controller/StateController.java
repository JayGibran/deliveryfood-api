package com.jaygibran.deliveryfood.api.v1.controller;

import com.jaygibran.deliveryfood.api.v1.assembler.StateDTOAssembler;
import com.jaygibran.deliveryfood.api.v1.assembler.StateInputDisassembler;
import com.jaygibran.deliveryfood.api.v1.model.StateDTO;
import com.jaygibran.deliveryfood.api.v1.model.input.StateInput;
import com.jaygibran.deliveryfood.domain.model.State;
import com.jaygibran.deliveryfood.domain.repository.StateRepository;
import com.jaygibran.deliveryfood.domain.service.StateRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

@AllArgsConstructor
@RestController
@RequestMapping(path = "/states", produces = MediaType.APPLICATION_JSON_VALUE)
public class StateController {

    private StateRepository stateRepository;

    private StateRegistryService stateRegistryService;

    private StateDTOAssembler stateDTOAssembler;

    private StateInputDisassembler stateInputDisassembler;

    @GetMapping
    public CollectionModel<StateDTO> list() {
        return stateDTOAssembler.toCollectionModel(this.stateRepository.findAll());
    }

    @GetMapping("/{id}")
    public StateDTO search(@PathVariable Long id) {
        return stateDTOAssembler.toModel(this.stateRegistryService.findOrFail(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StateDTO save(@RequestBody @Valid StateInput stateInput) {

        State state = stateInputDisassembler.toDomain(stateInput);

        return stateDTOAssembler.toModel(stateRegistryService.save(state));
    }

    @PutMapping("/{id}")
    public StateDTO update(@PathVariable Long id, @RequestBody @Valid StateInput stateInput) {
        State stateToUpdate = this.stateRegistryService.findOrFail(id);

        stateInputDisassembler.copyToDomainObject(stateInput, stateToUpdate);

        return stateDTOAssembler.toModel(stateRegistryService.save(stateToUpdate));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        stateRegistryService.delete(id);
    }
}
