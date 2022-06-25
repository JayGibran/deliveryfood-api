package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.EntityInUseException;
import com.jaygibran.deliveryfood.domain.exception.PaymentMethodNotFoundException;
import com.jaygibran.deliveryfood.domain.model.PaymentMethod;
import com.jaygibran.deliveryfood.domain.repository.PaymentMethodRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class PaymentMethodRegistryService {

    public static final String MSG_PAYMENT_METHOD_BEING_USED = "Payment Method of id %d can't be removed because is being used";
    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethod searchOrFail(Long id) {
        return this.paymentMethodRepository
                .findById(id)
                .orElseThrow(() -> new PaymentMethodNotFoundException(id));
    }

    @Transactional
    public PaymentMethod save(PaymentMethod paymentMethod) {
        return this.paymentMethodRepository.save(paymentMethod);
    }

    @Transactional
    public void delete(Long id) {
        try {
            this.paymentMethodRepository.deleteById(id);
            this.paymentMethodRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PaymentMethodNotFoundException(id);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException(String.format(MSG_PAYMENT_METHOD_BEING_USED, id));
        }
    }
}
