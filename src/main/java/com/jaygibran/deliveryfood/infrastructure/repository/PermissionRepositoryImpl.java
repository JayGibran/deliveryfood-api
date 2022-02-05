package com.jaygibran.deliveryfood.infrastructure.repository;

import com.jaygibran.deliveryfood.domain.model.PaymentMethod;
import com.jaygibran.deliveryfood.domain.model.Permission;
import com.jaygibran.deliveryfood.domain.repository.PermissionRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class PermissionRepositoryImpl implements PermissionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Permission> all() {
        return this.entityManager.createQuery("select k from Permission k", Permission.class).getResultList();
    }

    @Override
    public Permission findById(Long id) {
        return this.entityManager.find(Permission.class, id);
    }

    @Transactional
    @Override
    public Permission save(Permission permission) {
        return this.entityManager.merge(permission);
    }

    @Override
    public void delete(Permission permission) {
        permission = findById(permission.getId());
        this.entityManager.remove(permission);
    }
}
