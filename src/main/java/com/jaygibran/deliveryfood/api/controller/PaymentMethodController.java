package com.jaygibran.deliveryfood.api.controller;

import com.jaygibran.deliveryfood.api.assembler.PaymentMethodDTOAssembler;
import com.jaygibran.deliveryfood.api.assembler.PaymentMethodDTODisassembler;
import com.jaygibran.deliveryfood.api.model.PaymentMethodDTO;
import com.jaygibran.deliveryfood.api.model.input.PaymentMethodInput;
import com.jaygibran.deliveryfood.domain.model.PaymentMethod;
import com.jaygibran.deliveryfood.domain.repository.PaymentMethodRepository;
import com.jaygibran.deliveryfood.domain.service.PaymentMethodRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

@AllArgsConstructor
@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    private final PaymentMethodRegistryService paymentMethodRegistryService;

    private final PaymentMethodRepository paymentMethodRepository;

    private final PaymentMethodDTOAssembler paymentMethodDTOAssembler;

    private final PaymentMethodDTODisassembler paymentMethodDTODisassembler;

    @GetMapping
    public List<PaymentMethodDTO> list() {
        return paymentMethodDTOAssembler.toCollectionDTO(this.paymentMethodRepository.findAll());
    }

    @GetMapping("/{id}")
    public PaymentMethodDTO search(@PathVariable Long id) {
        return paymentMethodDTOAssembler.toDTO(paymentMethodRegistryService.findOrFail(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentMethodDTO add(@RequestBody @Valid PaymentMethodInput paymentMethodInput) {
        PaymentMethod paymentMethod = paymentMethodDTODisassembler.toDomain(paymentMethodInput);

        return paymentMethodDTOAssembler.toDTO(paymentMethodRegistryService.save(paymentMethod));
    }

    @PutMapping("/{id}")
    public PaymentMethodDTO update(@PathVariable Long id, @RequestBody @Valid PaymentMethodInput paymentMethodInput) {
        PaymentMethod paymentMethodToUpdate = paymentMethodRegistryService.findOrFail(id);

        this.paymentMethodDTODisassembler.copyToDomainObject(paymentMethodInput, paymentMethodToUpdate);

        return paymentMethodDTOAssembler.toDTO(this.paymentMethodRegistryService.save(paymentMethodToUpdate));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.paymentMethodRegistryService.delete(id);
    }
}
