package com.jaygibran.deliveryfood.api.assembler;

import com.jaygibran.deliveryfood.api.model.input.PaymentMethodInput;
import com.jaygibran.deliveryfood.domain.model.PaymentMethod;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PaymentMethodDTODisassembler {

    private final ModelMapper modelMapper;

    public PaymentMethod toDomain(PaymentMethodInput paymentMethodInput) {
        return modelMapper.map(paymentMethodInput, PaymentMethod.class);
    }

    public void copyToDomainObject(PaymentMethodInput paymentMethodInput, PaymentMethod paymentMethod) {
        modelMapper.map(paymentMethodInput, paymentMethod);
    }
}
