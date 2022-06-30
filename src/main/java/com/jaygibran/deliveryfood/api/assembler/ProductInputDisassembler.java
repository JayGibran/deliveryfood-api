package com.jaygibran.deliveryfood.api.assembler;

import com.jaygibran.deliveryfood.api.model.ProductDTO;
import com.jaygibran.deliveryfood.api.model.input.ProductInput;
import com.jaygibran.deliveryfood.domain.model.Product;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProductInputDisassembler {

    private final ModelMapper modelMapper;

    public Product toDomain(ProductInput productInput) {
        return modelMapper.map(productInput, Product.class);
    }

    public void copyToDomainObject(ProductInput productInput, Product productToUpdate) {
        modelMapper.map(productInput, productToUpdate);
    }
}
