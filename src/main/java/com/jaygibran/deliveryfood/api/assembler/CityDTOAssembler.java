package com.jaygibran.deliveryfood.api.assembler;

import com.jaygibran.deliveryfood.api.model.CityDTO;
import com.jaygibran.deliveryfood.domain.model.City;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CityDTOAssembler {

    private final ModelMapper modelMapper;

    public CityDTO toDTO(City city) {
        return modelMapper.map(city, CityDTO.class);
    }

    public List<CityDTO> toCollectionDTO(List<City> cities) {
        return cities.stream()
                .map(city -> toDTO(city))
                .collect(Collectors.toList());
    }
}
