package com.jaygibran.deliveryfood.api.v1.assembler;

import com.jaygibran.deliveryfood.api.v1.model.ProductPhotoDTO;
import com.jaygibran.deliveryfood.domain.model.ProductPhoto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProductPhotoDTOAssembler {

    private final ModelMapper modelMapper;

    public ProductPhotoDTO toDTO(ProductPhoto state) {
        return modelMapper.map(state, ProductPhotoDTO.class);
    }
    
}
