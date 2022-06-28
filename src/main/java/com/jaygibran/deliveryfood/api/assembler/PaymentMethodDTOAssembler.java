package com.jaygibran.deliveryfood.api.assembler;

import com.jaygibran.deliveryfood.api.model.PaymentMethodDTO;
import com.jaygibran.deliveryfood.domain.model.PaymentMethod;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PaymentMethodDTOAssembler {

    private final ModelMapper modelMapper;

    public PaymentMethodDTO toDTO(PaymentMethod paymentMethod) {
        return this.modelMapper.map(paymentMethod, PaymentMethodDTO.class);
    }

    public List<PaymentMethodDTO> toCollectionDTO(Collection<PaymentMethod> paymentMethods) {
        return paymentMethods
                .stream()
                .map(paymentMethod -> toDTO(paymentMethod)).collect(Collectors.toList());
    }
}
