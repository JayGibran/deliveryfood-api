package com.jaygibran.deliveryfood.api.assembler;

import com.jaygibran.deliveryfood.api.model.ProductDTO;
import com.jaygibran.deliveryfood.domain.model.Product;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ProductDTOAssembler {

    private final ModelMapper modelMapper;

    public ProductDTO toDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public List<ProductDTO> toCollectionDTO(List<Product> products) {
        return products
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
