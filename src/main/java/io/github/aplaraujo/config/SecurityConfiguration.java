package io.github.aplaraujo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //Ambas anotações permitem configurações no Spring Security
public class SecurityConfiguration {
    // HttpSecurity - objeto de contexto que faz parte do Spring Security, disponibilizado para fazer configurações
    // authorizeHttpRequests - personaliza configuraçoes
    // Injetar o objeto de autenticação
    // Injetar o filtro
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MasterPasswordAuthenticationProvider masterPasswordAuthenticationProvider, CustomFilter customFilter) throws Exception {
        return http
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
}
