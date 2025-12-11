package io.github.aplaraujo.domain.entity.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

// Classe de autenticação personalizada
public class CustomAuthentication implements Authentication {
    private final UserIdentity userIdentity;

    public CustomAuthentication(UserIdentity userIdentity) {
        if (userIdentity == null) {
            throw new ExceptionInInitializerError("It is not possible to create a custom authentication without user identity");
        }
        this.userIdentity = userIdentity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userIdentity.getRoles().stream().map(role -> new SimpleGrantedAuthority(role)).toList();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.userIdentity;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return this.userIdentity.getName();
    }
}
