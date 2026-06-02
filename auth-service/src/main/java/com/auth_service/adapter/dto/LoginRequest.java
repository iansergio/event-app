package com.auth_service.adapter.dto;

public record LoginRequest(
    String username,
    String password
) {
}