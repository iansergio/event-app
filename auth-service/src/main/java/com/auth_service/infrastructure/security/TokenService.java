package com.auth_service.infrastructure.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.auth_service.domain.UserRole;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private static final String HMAC_ALGORITHM = "HmacSHA256";

    private final byte[] secret;
    private final Duration ttl;

    public TokenService(
        @Value("${app.security.token-secret}") String tokenSecret,
        @Value("${app.security.token-ttl-minutes:120}") long ttlMinutes
    ) {
        this.secret = tokenSecret.getBytes(StandardCharsets.UTF_8);
        this.ttl = Duration.ofMinutes(ttlMinutes);
    }

    public String createToken(UUID userId, UserRole role) {
        long expiresAt = Instant.now().plus(ttl).toEpochMilli();
        String payload = userId + ":" + role.name() + ":" + expiresAt;
        String encodedPayload = encode(payload.getBytes(StandardCharsets.UTF_8));
        String signature = encode(sign(payload));
        return encodedPayload + "." + signature;
    }

    public UUID extractUserId(String token) {
        String payload = decodeAndVerify(token);
        String[] parts = payload.split(":");
        return UUID.fromString(parts[0]);
    }

    public UserRole extractRole(String token) {
        String payload = decodeAndVerify(token);
        String[] parts = payload.split(":");
        return UserRole.valueOf(parts[1]);
    }

    public Instant extractExpiresAt(String token) {
        String payload = decodeAndVerify(token);
        String[] parts = payload.split(":");
        return Instant.ofEpochMilli(Long.parseLong(parts[2]));
    }

    private String decodeAndVerify(String token) {
        if (token == null || !token.contains(".")) {
            throw new IllegalArgumentException("Token invalido");
        }

        String[] parts = token.split("\\.");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Token invalido");
        }

        String payload = new String(decode(parts[0]), StandardCharsets.UTF_8);
        byte[] expectedSignature = sign(payload);
        byte[] providedSignature = decode(parts[1]);

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
            throw new IllegalStateException("Falha ao assinar token", exception);
        }
    }

    private static String encode(byte[] value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value);
    }

    private static byte[] decode(String value) {
        return Base64.getUrlDecoder().decode(value);
    }
}