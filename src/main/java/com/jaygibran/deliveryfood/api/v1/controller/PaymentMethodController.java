package com.jaygibran.deliveryfood.api.v1.controller;

import com.jaygibran.deliveryfood.api.v1.assembler.PaymentMethodDTOAssembler;
import com.jaygibran.deliveryfood.api.v1.assembler.PaymentMethodDTODisassembler;
import com.jaygibran.deliveryfood.api.v1.model.PaymentMethodDTO;
import com.jaygibran.deliveryfood.api.v1.model.input.PaymentMethodInput;
import com.jaygibran.deliveryfood.api.v1.openapi.controller.PaymentMethodControllerOpenApi;
import com.jaygibran.deliveryfood.domain.model.PaymentMethod;
import com.jaygibran.deliveryfood.domain.repository.PaymentMethodRepository;
import com.jaygibran.deliveryfood.domain.service.PaymentMethodRegistryService;
import lombok.AllArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentMethodController implements PaymentMethodControllerOpenApi {

    private final PaymentMethodRegistryService paymentMethodRegistryService;

    private final PaymentMethodRepository paymentMethodRepository;

    private final PaymentMethodDTOAssembler paymentMethodDTOAssembler;

    private final PaymentMethodDTODisassembler paymentMethodDTODisassembler;

    @GetMapping
    public ResponseEntity<List<PaymentMethodDTO>> list() {
        List<PaymentMethodDTO> paymentMethodDTOS = paymentMethodDTOAssembler.toCollectionDTO(this.paymentMethodRepository.findAll());
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(paymentMethodDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodDTO> search(@PathVariable Long id, ServletWebRequest request) {

        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        PaymentMethodDTO paymentMethodDTO = paymentMethodDTOAssembler.toDTO(paymentMethodRegistryService.findOrFail(id));
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(paymentMethodDTO);
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
