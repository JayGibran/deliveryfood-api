package com.jaygibran.deliveryfood.infrastructure.repository;

import com.jaygibran.deliveryfood.domain.model.Kitchen;
import com.jaygibran.deliveryfood.domain.model.PaymentMethod;
import com.jaygibran.deliveryfood.domain.repository.PaymentMethodRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class PaymentRepositoryImpl implements PaymentMethodRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PaymentMethod> all() {
        return this.entityManager.createQuery("select p from PaymentMethod p", PaymentMethod.class).getResultList();
    }

    @Override
    public PaymentMethod findById(Long id) {
        return this.entityManager.find(PaymentMethod.class, id);
    }

    @Transactional
    @Override
    public PaymentMethod save(PaymentMethod paymentMethod) {
        return this.entityManager.merge(paymentMethod);
    }

    @Override
    public void delete(PaymentMethod paymentMethod) {
        paymentMethod = findById(paymentMethod.getId());
        this.entityManager.remove(paymentMethod);
    }
}
