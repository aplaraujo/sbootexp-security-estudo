package io.github.aplaraujo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //Ambas anotações permitem configurações no Spring Security
public class SecurityConfiguration {
    // HttpSecurity - objeto de contexto que faz parte do Spring Security, disponibilizado para fazer configurações
    // authorizeHttpRequests - personaliza configuraçoes
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(customizer -> {
                    customizer.requestMatchers("/public").permitAll(); // Permissão de rotas
                    customizer.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults()) // Autenticação padrão sem personalizações
                .formLogin(Customizer.withDefaults()) // Habilitação de formulário de registro / login
                .build();
    }
}
