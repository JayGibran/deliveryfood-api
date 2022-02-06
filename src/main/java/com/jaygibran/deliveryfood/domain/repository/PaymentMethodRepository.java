package com.jaygibran.deliveryfood.domain.repository;

import com.jaygibran.deliveryfood.domain.model.PaymentMethod;

import java.util.List;

public interface PaymentMethodRepository {

    List<PaymentMethod> all();
    PaymentMethod findById(Long id);
    PaymentMethod save(PaymentMethod paymentMethod);
    void delete (PaymentMethod paymentMethod);
}
