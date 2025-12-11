package io.github.aplaraujo.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// API de RH
@RestController
@RequestMapping("/rh")
public class RhController {

    @GetMapping("/tecnico")
    @PreAuthorize("hasAnyAuthority('TECNICO_RH', 'GERENTE_RH', 'ADMIN')")
    public ResponseEntity<String> tecnico() {
        return ResponseEntity.ok("Rota do técnico");
    }

    @GetMapping("/gerente")
    @PreAuthorize("hasAnyAuthority('GERENTE_RH', 'ADMIN')")
    public ResponseEntity<String> gerente() {
        return ResponseEntity.ok("Rota do gerente");
    }
}
