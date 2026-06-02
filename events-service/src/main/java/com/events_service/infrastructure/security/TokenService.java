package com.events_service.infrastructure.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private static final String HMAC_ALGORITHM = "HmacSHA256";

    private final byte[] secret;

    public TokenService(@Value("${app.security.token-secret}") String tokenSecret) {
        this.secret = tokenSecret.getBytes(StandardCharsets.UTF_8);
    }

    public TokenData resolveToken(String token) {
        String payload = decodeAndVerify(token);
        String[] parts = payload.split(":");
        return new TokenData(UUID.fromString(parts[0]), parts[1]);
    }

    private String decodeAndVerify(String token) {
        if (token == null || !token.contains(".")) {
            throw new IllegalArgumentException("Token invalido");
        }

        String[] parts = token.split("\\.");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Token invalido");
        }

        String payload = new String(Base64.getUrlDecoder().decode(parts[0]), StandardCharsets.UTF_8);
        byte[] expectedSignature = sign(payload);
        byte[] providedSignature = Base64.getUrlDecoder().decode(parts[1]);

        if (!MessageDigest.isEqual(expectedSignature, providedSignature)) {
            throw new IllegalArgumentException("Token invalido");
        }

        String[] payloadParts = payload.split(":");
        if (payloadParts.length != 3) {
            throw new IllegalArgumentException("Token invalido");
        }

        long expiresAt = Long.parseLong(payloadParts[2]);
        if (Instant.now().toEpochMilli() > expiresAt) {
            throw new IllegalArgumentException("Token expirado");
        }

        return payload;
    }

    private byte[] sign(String payload) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(secret, HMAC_ALGORITHM));
            return mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
        } catch (Exception exception) {
            throw new IllegalStateException("Falha ao validar token", exception);
        }
    }

    public record TokenData(UUID userId, String role) {
    }
}