package com.auth_service.adapter.dto;

import java.util.UUID;

public record LoginResponse(
    String token,
    UUID userId,
    String username,
    String role,
    long expiresAtEpochMillis
) {
}