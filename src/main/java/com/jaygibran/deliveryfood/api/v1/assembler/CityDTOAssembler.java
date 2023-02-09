package com.jaygibran.deliveryfood.api.v1.assembler;

import com.jaygibran.deliveryfood.api.v1.controller.CityController;
import com.jaygibran.deliveryfood.api.v1.controller.StateController;
import com.jaygibran.deliveryfood.api.v1.model.CityDTO;
import com.jaygibran.deliveryfood.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CityDTOAssembler extends RepresentationModelAssemblerSupport<City, CityDTO> {

    private final ModelMapper modelMapper;

    public CityDTOAssembler(ModelMapper modelMapper) {
        super(CityController.class, CityDTO.class);
        this.modelMapper = modelMapper;
    }
    
    @Override
    public CityDTO toModel(City city) {
        CityDTO cityDTO = modelMapper.map(city, CityDTO.class);
        
        cityDTO.add(linkTo(methodOn(CityController.class).search(cityDTO.getId()))
                .withSelfRel());
        
        cityDTO.add(linkTo(methodOn(CityController.class).list())
                .withRel("cities"));
        
        cityDTO.add(linkTo(methodOn(StateController.class).search(cityDTO.getState().getId()))
                .withSelfRel());

        return cityDTO;
    }

    @Override
    public CollectionModel<CityDTO> toCollectionModel(Iterable<? extends City> entities) {
        return super.toCollectionModel(entities).add(linkTo(CityController.class).withSelfRel());
    }
}
