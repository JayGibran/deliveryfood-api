package com.jaygibran.deliveryfood.api.controller;


import com.jaygibran.deliveryfood.api.assembler.CuisineDTOAssembler;
import com.jaygibran.deliveryfood.api.assembler.CuisineInputDisassembler;
import com.jaygibran.deliveryfood.api.model.CuisineDTO;
import com.jaygibran.deliveryfood.api.model.input.CuisineInput;
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
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/cuisines")
public class CuisineController {

    private CuisineRegistryService cuisineRegistryService;

    private CuisineRepository cuisineRepository;

    private CuisineDTOAssembler cuisineDTOAssembler;

    private CuisineInputDisassembler cuisineInputDisassembler;

    @GetMapping
    public List<CuisineDTO> list() {
        return cuisineDTOAssembler.toCollectionDTO(this.cuisineRepository.findAll());
    }

    @GetMapping("/{id}")
    public CuisineDTO search(@PathVariable Long id) {
        return cuisineDTOAssembler.toDTO(cuisineRegistryService.searchOrFail(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuisineDTO add(@RequestBody @Valid CuisineInput cuisineInput) {
        Cuisine cuisine = cuisineInputDisassembler.toDomain(cuisineInput);

        return cuisineDTOAssembler.toDTO(cuisineRegistryService.save(cuisine));
    }

    @PutMapping("/{id}")
    public CuisineDTO update(@PathVariable Long id, @RequestBody @Valid CuisineInput cuisineInput) {
        Cuisine cuisineToUpdate = cuisineRegistryService.searchOrFail(id);

        this.cuisineInputDisassembler.copyToDomainObject(cuisineInput, cuisineToUpdate);
    
        return cuisineDTOAssembler.toDTO(this.cuisineRegistryService.save(cuisineToUpdate));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.cuisineRegistryService.delete(id);
    }
}
