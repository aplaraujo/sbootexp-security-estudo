package io.github.aplaraujo.domain.entity.security;

import io.github.aplaraujo.domain.entity.User;
import io.github.aplaraujo.domain.entity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

// Criar um fornecedor de autenticação (Authentication Provider) que vai ler a base de usuários, ler o método do Custom Authentication durante o fluxo de autenticação
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserService service;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Autenticação com a base de usuários (verificar se o usuário e a senha coincidem)
        User user = service.getUserWithRoles(login);
        // matches - método usado para verificar se a senha criptografada é igual à senha digitada
        boolean passwordsMatch = encoder.matches(password, user.getPassword());
        if(user == null) {
            throw new BadCredentialsException("User not found!");
        }

        System.out.println("USER: " + user.getLogin());
        System.out.println("ROLES DO BANCO: " + user.getRoles());
        System.out.println("QUANTIDADE DE ROLES: " + user.getRoles().size());

        if (!passwordsMatch) {
            throw new BadCredentialsException("Password not valid!");
        }

        List<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();

        System.out.println("AUTHORITIES CRIADAS:");
        authorities.forEach(a -> System.out.println("  - " + a.getAuthority()));

        UserIdentity userIdentity = new UserIdentity(user.getId(), user.getName(), user.getLogin(), user.getRoles());

        return new UsernamePasswordAuthenticationToken(userIdentity, null, authorities);
    }

    // Esse método vai dizer se suporta a classe que é o parâmetro
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
