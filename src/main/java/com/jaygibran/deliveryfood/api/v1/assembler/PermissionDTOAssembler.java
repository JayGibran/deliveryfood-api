package com.jaygibran.deliveryfood.api.v1.assembler;

import com.jaygibran.deliveryfood.api.v1.model.PermissionDTO;
import com.jaygibran.deliveryfood.domain.model.Permission;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PermissionDTOAssembler {

    private final ModelMapper modelMapper;

    public PermissionDTO toDTO(Permission permission) {
        return this.modelMapper.map(permission, PermissionDTO.class);
    }

    public List<PermissionDTO> toCollectionDTO(Collection<Permission> permissions) {
        return permissions
                .stream()
                .map(permission -> toDTO(permission)).collect(Collectors.toList());
    }
}
