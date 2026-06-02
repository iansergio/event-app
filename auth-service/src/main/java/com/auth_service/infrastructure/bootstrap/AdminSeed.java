package com.auth_service.infrastructure.bootstrap;

import com.auth_service.domain.User;
import com.auth_service.domain.UserRepository;
import com.auth_service.domain.UserRole;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminSeed implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String username;
    private final String password;

    public AdminSeed(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        @Value("${app.security.bootstrap-admin.username:admin}") String username,
        @Value("${app.security.bootstrap-admin.password:admin123}") String password
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run(ApplicationArguments args) {
        String normalizedUsername = username.trim().toLowerCase();
        if (!userRepository.existsByUsername(normalizedUsername)) {
            userRepository.save(new User(normalizedUsername, passwordEncoder.encode(password), UserRole.ADMIN));
        }
    }
}