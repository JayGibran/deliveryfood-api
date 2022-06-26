package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.api.assembler.GroupDTOAssembler;
import com.jaygibran.deliveryfood.api.assembler.GroupInputDisassembler;
import com.jaygibran.deliveryfood.api.model.GroupDTO;
import com.jaygibran.deliveryfood.api.model.input.GroupInput;
import com.jaygibran.deliveryfood.domain.model.Group;
import com.jaygibran.deliveryfood.domain.repository.GroupRepository;
import com.jaygibran.deliveryfood.domain.service.GroupRegistryService;
import lombok.AllArgsConstructor;
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
@RequestMapping("/groups")
public class GroupController {

    private GroupRegistryService groupRegistryService;

    private GroupRepository groupRepository;

    private GroupDTOAssembler groupDTOAssembler;

    private GroupInputDisassembler groupInputDisassembler;

    @GetMapping
    public List<GroupDTO> list() {
        return groupDTOAssembler.toCollectionDTO(this.groupRepository.findAll());
    }

    @GetMapping("/{id}")
    public GroupDTO search(@PathVariable Long id) {
        return groupDTOAssembler.toDTO(groupRegistryService.searchOrFail(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDTO add(@RequestBody @Valid GroupInput groupInput) {
        Group group = groupInputDisassembler.toDomain(groupInput);

        return groupDTOAssembler.toDTO(groupRegistryService.save(group));
    }

    @PutMapping("/{id}")
    public GroupDTO update(@PathVariable Long id, @RequestBody @Valid GroupInput groupInput) {
        Group groupToUpdate = groupRegistryService.searchOrFail(id);

        this.groupInputDisassembler.copyToDomainObject(groupInput, groupToUpdate);

        return groupDTOAssembler.toDTO(this.groupRegistryService.save(groupToUpdate));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.groupRegistryService.delete(id);
    }

}
