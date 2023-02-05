package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.api.assembler.StateDTOAssembler;
import com.jaygibran.deliveryfood.api.assembler.StateInputDisassembler;
import com.jaygibran.deliveryfood.api.model.StateDTO;
import com.jaygibran.deliveryfood.api.model.input.StateInput;
import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.exception.EntityNotFoundException;
import com.jaygibran.deliveryfood.domain.model.City;
import com.jaygibran.deliveryfood.domain.model.State;
import com.jaygibran.deliveryfood.domain.repository.StateRepository;
import com.jaygibran.deliveryfood.domain.service.StateRegistryService;
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

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/states", produces = MediaType.APPLICATION_JSON_VALUE)
public class StateController {

    private StateRepository stateRepository;

    private StateRegistryService stateRegistryService;

    private StateDTOAssembler stateDTOAssembler;

    private StateInputDisassembler stateInputDisassembler;

    @GetMapping
    public List<StateDTO> list() {
        return stateDTOAssembler.toCollectionDTO(this.stateRepository.findAll());
    }

    @GetMapping("/{id}")
    public StateDTO search(@PathVariable Long id) {
        return stateDTOAssembler.toDTO(this.stateRegistryService.findOrFail(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StateDTO save(@RequestBody @Valid StateInput stateInput) {

        State state = stateInputDisassembler.toDomain(stateInput);

        return stateDTOAssembler.toDTO(stateRegistryService.save(state));
    }

    @PutMapping("/{id}")
    public StateDTO update(@PathVariable Long id, @RequestBody @Valid StateInput stateInput) {
        State stateToUpdate = this.stateRegistryService.findOrFail(id);

        stateInputDisassembler.copyToDomainObject(stateInput, stateToUpdate);

        return stateDTOAssembler.toDTO(stateRegistryService.save(stateToUpdate));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        stateRegistryService.delete(id);
    }
}
