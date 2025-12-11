package io.github.aplaraujo.api;

import io.github.aplaraujo.domain.entity.Role;
import io.github.aplaraujo.domain.entity.dto.RoleDTO;
import io.github.aplaraujo.domain.entity.mapper.RoleMapper;
import io.github.aplaraujo.domain.entity.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController implements GenericController{
    private final RoleService service;
    private final RoleMapper mapper;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> salvar(@RequestBody RoleDTO dto) {
        Role role = mapper.toEntity(dto);
        service.save(role);
        URI location = generateHeaderLocation(role.getId().toString());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleDTO> roles() {
        return service.roles();
    }
}
