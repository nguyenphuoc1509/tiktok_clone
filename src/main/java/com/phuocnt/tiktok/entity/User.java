package com.phuocnt.tiktok.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id", updatable = false, nullable = false)
    UUID userId;

    @Column(nullable = false, unique = true, length = 50)
    String username;

    @Column(nullable = false, unique = true, length = 100)
    String email;

    @Column(name = "password_hash", nullable = false)
    String passwordHash;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    Instant createdAt;

    Instant lastLoginAt;

    String profilePictureUrl;

    @Column(length = 500)
    String bio;

    @Column(nullable = false)
    boolean isCreator = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @ToString.Exclude
    java.util.Set<Role> roles = new java.util.HashSet<>();
}
