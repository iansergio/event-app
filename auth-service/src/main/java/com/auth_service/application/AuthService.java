package com.auth_service.application;

import com.auth_service.adapter.dto.LoginRequest;
import com.auth_service.adapter.dto.LoginResponse;
import com.auth_service.adapter.dto.RegisterRequest;
import com.auth_service.domain.User;
import com.auth_service.domain.UserRepository;
import com.auth_service.infrastructure.security.TokenService;

import java.time.Instant;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(TokenService tokenService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public LoginResponse register(RegisterRequest request) {
        if (request == null || request.username() == null || request.password() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username e password sao obrigatorios");
        }

        String username = request.username().trim().toLowerCase();
        if (username.isBlank() || request.password().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username e password sao obrigatorios");
        }

        if (request.confirmPassword() == null || !request.password().equals(request.confirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password e confirmPassword devem ser iguais");
        }
        if (userRepository.existsByUsername(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuario ja cadastrado");
        }

        User user = new User(username, passwordEncoder.encode(request.password().trim()));
        user = userRepository.save(user);
        return issueToken(user);
    }

    public LoginResponse login(LoginRequest request) {
        if (request == null || request.username() == null || request.password() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username e password sao obrigatorios");
        }

        String username = request.username().trim().toLowerCase();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais invalidas"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais invalidas");
        }

        return issueToken(user);
    }

    private LoginResponse issueToken(User user) {
        UUID userId = user.getId();
        String token = tokenService.createToken(userId, user.getRole());
        Instant expiresAt = tokenService.extractExpiresAt(token);

        return new LoginResponse(
            token,
            userId,
            user.getUsername(),
            user.getRole().name(),
            expiresAt.toEpochMilli()
        );
    }
}