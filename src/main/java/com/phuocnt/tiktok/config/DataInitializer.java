package com.phuocnt.tiktok.config;

import com.phuocnt.tiktok.entity.User;
import com.phuocnt.tiktok.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = User.builder()
                        .username("admin")
                        .email("admin@example.com")
                        .passwordHash(passwordEncoder.encode("123456"))
                        .bio("Admin user")
                        .isCreator(true)
                        .build();

                User test = User.builder()
                        .username("testuser")
                        .email("test@example.com")
                        .passwordHash(passwordEncoder.encode("123456"))
                        .bio("Normal test user")
                        .isCreator(false)
                        .build();

                userRepository.save(admin);
                userRepository.save(test);
            }
        };
    }
}
