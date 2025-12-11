package io.github.aplaraujo.api;

import io.github.aplaraujo.domain.entity.dto.UserRequestDTO;
import io.github.aplaraujo.domain.entity.dto.UserResponseDTO;
import io.github.aplaraujo.domain.entity.mapper.UserMapper;
import io.github.aplaraujo.domain.entity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO dto) {
        UserResponseDTO response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
