package io.github.aplaraujo.domain.entity.repository;

import io.github.aplaraujo.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
