package io.github.aplaraujo.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {
    @GetMapping("/public")
    public ResponseEntity<String> publicRoute() {
        return ResponseEntity.ok("Public route ok!");
    }

    // Inclusão de autenticação no controlador
    @GetMapping("/private")
    public ResponseEntity<String> privateRoute(Authentication authentication) {
        return ResponseEntity.ok("Private route ok! User connected: " + authentication.getName());
    }

    // Rota de administrador
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')") // Define uma autorização para um determinado perfil
    public ResponseEntity<String> adminRoute() {
        return ResponseEntity.ok("Admin route ok!");
    }
}
