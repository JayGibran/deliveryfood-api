package com.jaygibran.deliveryfood.api.v1.controller;

import com.jaygibran.deliveryfood.api.v1.assembler.PermissionDTOAssembler;
import com.jaygibran.deliveryfood.api.v1.model.PermissionDTO;
import com.jaygibran.deliveryfood.core.security.CheckSecurity;
import com.jaygibran.deliveryfood.domain.model.Permission;
import com.jaygibran.deliveryfood.domain.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionController {

    private final PermissionRepository permissionRepository;

    private final PermissionDTOAssembler permissionDTOAssembler;

    @CheckSecurity.UsersGroupsPermissions.AllowQuery
    @GetMapping
    public List<PermissionDTO> list() {
        List<Permission> allPermissions = permissionRepository.findAll();
        return permissionDTOAssembler.toCollectionDTO(allPermissions);
    }
}
