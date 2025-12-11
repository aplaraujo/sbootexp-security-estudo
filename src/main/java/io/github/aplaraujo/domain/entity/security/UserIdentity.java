package io.github.aplaraujo.domain.entity.security;

import io.github.aplaraujo.domain.entity.Role;
import lombok.*;

import java.util.*;

// Definir uma classe que representa a identificação de usuários
@Data
@AllArgsConstructor
public class UserIdentity {
    private UUID id;
    private String name;
    private String login;
    private Set<Role> roles;

    public Set<Role> getRoles() {
        if (roles == null) {
            roles = new HashSet<>();
        }
        return roles;
    }
}
