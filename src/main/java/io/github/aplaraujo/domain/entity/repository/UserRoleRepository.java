package io.github.aplaraujo.domain.entity.repository;

import io.github.aplaraujo.domain.entity.Role;
import io.github.aplaraujo.domain.entity.User;
import io.github.aplaraujo.domain.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    @Query(""" 
        SELECT DISTINCT r.name
        FROM UserRole ur
        JOIN ur.role r
        JOIN ur.user u
        WHERE u = ?1
    """)
    Set<Role> findRolesByUser(User user);
}
