package com.jaygibran.deliveryfood.api.v1.controller;


import com.jaygibran.deliveryfood.api.v1.assembler.CuisineDTOAssembler;
import com.jaygibran.deliveryfood.api.v1.assembler.CuisineInputDisassembler;
import com.jaygibran.deliveryfood.api.v1.model.CuisineDTO;
import com.jaygibran.deliveryfood.api.v1.model.input.CuisineInput;
import com.jaygibran.deliveryfood.api.v1.openapi.controller.CuisineControllerOpenApi;
import com.jaygibran.deliveryfood.core.security.CheckSecurity;
import com.jaygibran.deliveryfood.domain.model.Cuisine;
import com.jaygibran.deliveryfood.domain.repository.CuisineRepository;
import com.jaygibran.deliveryfood.domain.service.CuisineRegistryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = "/v1/cuisines", produces = MediaType.APPLICATION_JSON_VALUE)
public class CuisineController implements CuisineControllerOpenApi {

    private CuisineRegistryService cuisineRegistryService;

    private CuisineRepository cuisineRepository;

    private CuisineDTOAssembler cuisineDTOAssembler;

    private CuisineInputDisassembler cuisineInputDisassembler;

    @CheckSecurity.Cuisines.AllowQuery
    @GetMapping
    public Page<CuisineDTO> list(Pageable pageable) {
        log.info("Querying cuisines...");

        Page<Cuisine> cuisinePages = this.cuisineRepository.findAll(pageable);

        List<CuisineDTO> cuisineDTOS = cuisineDTOAssembler.toCollectionDTO(cuisinePages.getContent());

        return new PageImpl<>(cuisineDTOS, pageable, cuisinePages.getTotalElements());
    }

    @CheckSecurity.Cuisines.AllowQuery
    @GetMapping("/{id}")
    public CuisineDTO search(@PathVariable Long id) {
        return cuisineDTOAssembler.toDTO(cuisineRegistryService.searchOrFail(id));
    }

    @CheckSecurity.Cuisines.AllowEdit
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuisineDTO add(@RequestBody @Valid CuisineInput cuisineInput) {
        Cuisine cuisine = cuisineInputDisassembler.toDomain(cuisineInput);

        return cuisineDTOAssembler.toDTO(cuisineRegistryService.save(cuisine));
    }

    @CheckSecurity.Cuisines.AllowEdit
    @PutMapping("/{id}")
    public CuisineDTO update(@PathVariable Long id, @RequestBody @Valid CuisineInput cuisineInput) {
        Cuisine cuisineToUpdate = cuisineRegistryService.searchOrFail(id);

        this.cuisineInputDisassembler.copyToDomainObject(cuisineInput, cuisineToUpdate);

        return cuisineDTOAssembler.toDTO(this.cuisineRegistryService.save(cuisineToUpdate));
    }

    @CheckSecurity.Cuisines.AllowEdit
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.cuisineRegistryService.delete(id);
    }
}
