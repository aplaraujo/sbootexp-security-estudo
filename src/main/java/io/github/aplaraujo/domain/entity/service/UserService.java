package io.github.aplaraujo.domain.entity.service;

import io.github.aplaraujo.domain.entity.Role;
import io.github.aplaraujo.domain.entity.User;
import io.github.aplaraujo.domain.entity.dto.UserRequestDTO;
import io.github.aplaraujo.domain.entity.dto.UserResponseDTO;
import io.github.aplaraujo.domain.entity.mapper.UserMapper;
import io.github.aplaraujo.domain.entity.repository.RoleRepository;
import io.github.aplaraujo.domain.entity.repository.UserRepository;
import io.github.aplaraujo.domain.entity.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;
    private final UserRoleRepository userRoleRepository;


    public UserResponseDTO create(UserRequestDTO request) {
        List<Role> roles = roleRepository.findByNameIn(request.roles());
        User user = mapper.toEntity(request, roles);
        user.setPassword(encoder.encode(request.password()));
        user = repository.save(user);
        return mapper.toResponse(user);
    }

    public User getUserWithRoles(String login) {
        Optional<User> userOptional = repository.findByLogin(login);
        if(userOptional.isEmpty()) {
            return null;
        }
        // Se o usuário existir, obter as permissões
        User user = userOptional.get();
        Set<Role> roles = userRoleRepository.findRolesByUser(user);
        user.setRoles(roles);
        return user;
    }
}
