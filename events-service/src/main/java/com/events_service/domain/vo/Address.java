package com.events_service.domain.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record Address(
    String street,
    String city,
    String state,
    String zipCode
) {
    public String getFullAddress() {
        return String.format("%s, %s - %s0", street, city, state);
    }
}