package io.github.aplaraujo.domain.entity.mapper;

import io.github.aplaraujo.domain.entity.Role;
import io.github.aplaraujo.domain.entity.dto.RoleDTO;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public Role toEntity(RoleDTO dto) {
        Role role = new Role();
        role.setName(dto.name());
        return role;
    }

    public RoleDTO toDTO(Role role) {
        return new RoleDTO(role.getId(), role.getName());
    }
}
