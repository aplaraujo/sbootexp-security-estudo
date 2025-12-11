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

        System.out.println("ROLES ENCONTRADAS NO BANCO:");
        roles.forEach(role -> System.out.println("  - " + role.getName()));

        User user = mapper.toEntity(request, roles);
        user.setPassword(encoder.encode(request.password()));
        user = repository.save(user);

        System.out.println("ROLES DO USUÁRIO SALVO:");
        user.getRoles().forEach(role -> System.out.println("  - " + role.getName()));


        return mapper.toResponse(user);
    }

    public User getUserWithRoles(String login) {
        Optional<User> userOptional = repository.findByLogin(login);
        if(userOptional.isEmpty()) {
            return null;
        }
        // Se o usuário existir, obter as permissões
        User user = userOptional.get();

        System.out.println("ROLES CARREGADAS AUTOMATICAMENTE:");
        user.getRoles().forEach(role -> System.out.println("  - " + role.getName()));
        return user;
    }
}
