package io.github.aplaraujo.domain.entity.service;

import io.github.aplaraujo.domain.entity.Role;
import io.github.aplaraujo.domain.entity.dto.RoleDTO;
import io.github.aplaraujo.domain.entity.mapper.RoleMapper;
import io.github.aplaraujo.domain.entity.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;
    private final RoleMapper mapper;

    public Role save(Role role) {
        return repository.save(role);
    }

    public List<RoleDTO> roles() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }
}
