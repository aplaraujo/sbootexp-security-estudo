package io.github.aplaraujo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Role - perfil do usuário (grupo de usuário) -> Exemplos: master, gerente, frente de loja, vendedor
// Authority - permissão específica que será concedida com base em um determinado perfil -> Exemplos: cadastro de usuário, tela de relatório
@Configuration
@EnableWebSecurity //Ambas anotações permitem configurações no Spring Security
@EnableMethodSecurity(securedEnabled = true) // Habilita a configuração de autorizações
public class SecurityConfiguration {
    // HttpSecurity - objeto de contexto que faz parte do Spring Security, disponibilizado para fazer configurações
    // authorizeHttpRequests - personaliza configuraçoes
    // Injetar o objeto de autenticação
    // Injetar o filtro
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MasterPasswordAuthenticationProvider masterPasswordAuthenticationProvider, CustomFilter customFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Desabilitar o filtro de CSRF
                .authorizeHttpRequests(customizer -> {
                    customizer.requestMatchers("/public").permitAll(); // Permissão de rotas
                    customizer.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults()) // Autenticação padrão sem personalizações
                .formLogin(Customizer.withDefaults()) // Habilitação de formulário de registro / login
                .authenticationProvider(masterPasswordAuthenticationProvider)
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Verificação e codificação de senha
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(16);
    }

    // Criação de uma base de usuários
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails commonUser = User.builder().username("user").password(passwordEncoder().encode("123")).roles("USER").build();
        UserDetails adminUser = User.builder().username("admin").password(passwordEncoder().encode("admin123")).roles("ADMIN", "USER").build();
        return new InMemoryUserDetailsManager(commonUser, adminUser);
    }

    // Definição de perfis (prefixo)
    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
