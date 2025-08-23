package com.phuocnt.tiktok.repository;

import com.phuocnt.tiktok.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    @EntityGraph(attributePaths = "roles")
    Optional<User> findWithRolesByUsername(String username);
}
