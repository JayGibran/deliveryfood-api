package com.jaygibran.deliveryfood.api.v1.controller;

import com.jaygibran.deliveryfood.api.v1.assembler.UserDTOAssembler;
import com.jaygibran.deliveryfood.api.v1.assembler.UserInputDisassembler;
import com.jaygibran.deliveryfood.api.v1.model.UserDTO;
import com.jaygibran.deliveryfood.api.v1.model.input.UserInput;
import com.jaygibran.deliveryfood.api.v1.model.input.UserUpdateInput;
import com.jaygibran.deliveryfood.api.v1.model.input.UserUpdatePasswordInput;
import com.jaygibran.deliveryfood.domain.model.User;
import com.jaygibran.deliveryfood.domain.repository.UserRepository;
import com.jaygibran.deliveryfood.domain.service.UserRegistryService;
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
@RequestMapping(path = "/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserRepository userRepository;

    private final UserRegistryService userRegistryService;

    private final UserDTOAssembler userDTOAssembler;

    private final UserInputDisassembler userInputDisassembler;

    @GetMapping
    public CollectionModel<UserDTO> list() {
        return userDTOAssembler.toCollectionModel(this.userRepository.findAll());
    }

    @GetMapping("/{id}")
    public UserDTO search(@PathVariable Long id) {
        return userDTOAssembler.toModel(this.userRegistryService.findOrFail(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO save(@RequestBody @Valid UserInput userInput) {

        User user = userInputDisassembler.toDomain(userInput);

        return userDTOAssembler.toModel(userRegistryService.save(user));
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Long id, @RequestBody @Valid UserUpdateInput userUpdateInput) {
        User userToUpdate = this.userRegistryService.findOrFail(id);

        userInputDisassembler.copyToDomainObject(userUpdateInput, userToUpdate);

        return userDTOAssembler.toModel(userRegistryService.save(userToUpdate));
    }

    @PutMapping("/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable Long id, @RequestBody @Valid UserUpdatePasswordInput userUpdatePasswordInput) {
        userRegistryService.updatePassword(id, userUpdatePasswordInput.getCurrentPassword(), userUpdatePasswordInput.getNewPassword());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRegistryService.delete(id);
    }
}
