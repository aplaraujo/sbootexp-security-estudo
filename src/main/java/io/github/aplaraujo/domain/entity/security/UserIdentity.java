package io.github.aplaraujo.domain.entity.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Definir uma classe que representa a identificação de usuários
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserIdentity {
    private UUID id;
    private String name;
    private String login;
    private List<String> roles;

    public List<String> getRoles() {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        return roles;
    }
}
