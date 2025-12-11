package io.github.aplaraujo.domain.entity.repository;

import io.github.aplaraujo.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    List<Role> findByNameIn(List<String> names);
}
