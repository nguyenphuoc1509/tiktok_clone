package com.phuocnt.tiktok.config;

import com.phuocnt.tiktok.entity.Role;
import com.phuocnt.tiktok.entity.User;
import com.phuocnt.tiktok.repository.RoleRepository;
import com.phuocnt.tiktok.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seedRolesUsers(RoleRepository roleRepo, UserRepository userRepo) {
        return args -> {
            var userRole = roleRepo.findByName("ROLE_USER").orElseGet(() -> roleRepo.save(Role.builder().name("ROLE_USER").build()));
            var adminRole = roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> roleRepo.save(Role.builder().name("ROLE_ADMIN").build()));

            if (userRepo.findByUsername("admin").isEmpty()) {
                var admin = User.builder()
                        .username("admin")
                        .email("admin@example.com")
                        .passwordHash(passwordEncoder.encode("123456"))
                        .isCreator(true)
                        .roles(Set.of(userRole, adminRole))
                        .build();
                userRepo.save(admin);
            }
            if (userRepo.findByUsername("user").isEmpty()) {
                var u = User.builder()
                        .username("user")
                        .email("user@example.com")
                        .passwordHash(passwordEncoder.encode("123456"))
                        .roles(Set.of(userRole))
                        .build();
                userRepo.save(u);
            }
        };
    }
}
