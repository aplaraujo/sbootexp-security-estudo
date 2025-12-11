package io.github.aplaraujo.domain.entity.service;

import io.github.aplaraujo.domain.entity.Role;
import io.github.aplaraujo.domain.entity.User;
import io.github.aplaraujo.domain.entity.dto.UserRequestDTO;
import io.github.aplaraujo.domain.entity.dto.UserResponseDTO;
import io.github.aplaraujo.domain.entity.mapper.UserMapper;
import io.github.aplaraujo.domain.entity.repository.RoleRepository;
import io.github.aplaraujo.domain.entity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;


    public UserResponseDTO create(UserRequestDTO request) {
        List<Role> roles = roleRepository.findByNameIn(request.roles());
        User user = mapper.toEntity(request, roles);
        user.setPassword(encoder.encode(request.password()));
        user = repository.save(user);
        return mapper.toResponse(user);
    }
}
