package io.github.aplaraujo.domain.entity.security;

import io.github.aplaraujo.domain.entity.User;
import io.github.aplaraujo.domain.entity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Criar um fornecedor de autenticação (Authentication Provider) que vai ler a base de usuários, ler o método do Custom Authentication durante o fluxo de autenticação
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserService service;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = (String) authentication.getCredentials();

        // Autenticação com a base de usuários (verificar se o usuário e a senha coincidem)
        User user = service.getUserWithRoles(login);
        // matches - método usado para verificar se a senha criptografada é igual à senha digitada
        if(user != null) {
            boolean passwordsMatch = encoder.matches(password, user.getPassword());
            if(passwordsMatch) {
                UserIdentity userIdentity = new UserIdentity(user.getId(), user.getName(), user.getLogin(), user.getRoles());
                return new CustomAuthentication(userIdentity);
            }
        }
        return null;
    }

    // Esse método vai dizer se suporta a classe que é o parâmetro
    @Override
    public boolean supports(Class<?> authentication) {
//        return true;
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
