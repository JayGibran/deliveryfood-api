package com.jaygibran.deliveryfood.domain.service;

import com.jaygibran.deliveryfood.domain.exception.PermissionNotFoundException;
import com.jaygibran.deliveryfood.domain.model.Permission;
import com.jaygibran.deliveryfood.domain.repository.PermissionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PermissionRegistryService {

    private final PermissionRepository permissionRepository;

    public Permission findOrFail(Long id) {
        return permissionRepository
                .findById(id)
                .orElseThrow(() -> new PermissionNotFoundException(id));
    }
}
