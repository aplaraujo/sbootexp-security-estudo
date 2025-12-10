package io.github.aplaraujo.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

// Authentication Provider: fornece uma autenticação
@Component
public class MasterPasswordAuthenticationProvider implements AuthenticationProvider {

    // Método para personalizar a autenticação
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var login = authentication.getName();
        var password = (String) authentication.getCredentials();

        String masterLogin = "master";
        String masterPassword = "master123";

        // Verificar se o login e a senha coincidem para fornecer a autenticação
        if(masterLogin.equals(login) && masterPassword.equals(password)) {
            return new UsernamePasswordAuthenticationToken("Master", null, List.of(new SimpleGrantedAuthority("ADMIN")));
        }
        return null;
    }

    // Verifica se existe suporte para um determinado tipo de autenticação
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
