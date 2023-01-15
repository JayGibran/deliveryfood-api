package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.api.ResourceUriHelper;
import com.jaygibran.deliveryfood.api.assembler.CityDTOAssembler;
import com.jaygibran.deliveryfood.api.assembler.CityInputDisassembler;
import com.jaygibran.deliveryfood.api.openapi.controller.CityControllerOpenApi;
import com.jaygibran.deliveryfood.api.exceptionhandler.Problem;
import com.jaygibran.deliveryfood.api.model.CityDTO;
import com.jaygibran.deliveryfood.api.model.input.CityInput;
import com.jaygibran.deliveryfood.domain.exception.BusinessException;
import com.jaygibran.deliveryfood.domain.exception.StateNotFoundException;
import com.jaygibran.deliveryfood.domain.model.City;
import com.jaygibran.deliveryfood.domain.repository.CityRepository;
import com.jaygibran.deliveryfood.domain.service.CityRegistryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController implements CityControllerOpenApi {

    private CityRepository cityRepository;

    private CityRegistryService cityRegistryService;

    private CityDTOAssembler cityDTOAssembler;

    private CityInputDisassembler cityInputDisassembler;

    @GetMapping
    public List<CityDTO> list() {
        return cityDTOAssembler.toCollectionDTO(cityRepository.findAll());
    }

    @GetMapping("/{id}")
    public CityDTO search(@PathVariable Long id) {
        CityDTO cityDTO = cityDTOAssembler.toDTO(cityRegistryService.findOrFail(id));

        cityDTO.add(linkTo(CityController.class)
                .slash(cityDTO.getId())
                .withSelfRel());

        cityDTO.add(linkTo(CityController.class)
                .withRel("/cities"));

        cityDTO.add(linkTo(StateController.class)
                .slash(cityDTO.getState().getId())
                .withSelfRel());

        return cityDTO;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CityDTO save(@RequestBody @Valid CityInput cityInput) {
        try {
            City city = cityInputDisassembler.toDomain(cityInput);

            CityDTO cityDTO = cityDTOAssembler.toDTO(cityRegistryService.save(city));

            ResourceUriHelper.addUriInResponseHeader(cityDTO.getId());

            return cityDTO;
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public CityDTO update(@PathVariable Long id,
                          @RequestBody @Valid CityInput cityInput) {
        try {
            City cityToUpdate = this.cityRegistryService.findOrFail(id);

            cityInputDisassembler.copyToDomainObject(cityInput, cityToUpdate);

            return cityDTOAssembler.toDTO(this.cityRegistryService.save(cityToUpdate));
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @ApiOperation("Delete city by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "City was deleted"),
            @ApiResponse(responseCode = "404", description = "City was not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@ApiParam(value = "City id", example = "1") @PathVariable Long id) {
        this.cityRegistryService.delete(id);
    }
}
