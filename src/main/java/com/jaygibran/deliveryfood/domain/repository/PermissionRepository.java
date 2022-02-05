package com.jaygibran.deliveryfood.domain.repository;

import com.jaygibran.deliveryfood.domain.model.Permission;

import java.util.List;

public interface PermissionRepository {

    List<Permission> all();
    Permission findById(Long id);
    Permission save(Permission kitchen);
    void delete (Permission kitchen);
}
