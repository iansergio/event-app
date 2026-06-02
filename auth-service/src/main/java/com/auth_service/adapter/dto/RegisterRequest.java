package com.auth_service.adapter.dto;

public record RegisterRequest(
    String username,
    String password,
    String confirmPassword
) {
}