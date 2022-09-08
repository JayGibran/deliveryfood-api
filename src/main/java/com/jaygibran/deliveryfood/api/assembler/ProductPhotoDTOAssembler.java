package com.jaygibran.deliveryfood.api.assembler;

import com.jaygibran.deliveryfood.api.model.ProductPhotoDTO;
import com.jaygibran.deliveryfood.api.model.StateDTO;
import com.jaygibran.deliveryfood.domain.model.ProductPhoto;
import com.jaygibran.deliveryfood.domain.model.State;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ProductPhotoDTOAssembler {

    private final ModelMapper modelMapper;

    public ProductPhotoDTO toDTO(ProductPhoto state) {
        return modelMapper.map(state, ProductPhotoDTO.class);
    }
    
}
