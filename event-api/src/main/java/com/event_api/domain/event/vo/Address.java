package com.event_api.domain.event.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class Address {
    private String street;
    private String number;
    private String city;
    private String state;
    private String zipCode;

    private Address(String street,
                    String number,
                    String city,
                    String state,
                    String zipCode) {

        this.street = validateRequired(street, "street");
        this.number = validateRequired(number, "number");
        this.city = validateRequired(city, "city");
        this.state = validateRequired(state, "state");
        this.zipCode = validateZip(zipCode);
    }

    public static Address of(String street,
                             String number,
                             String city,
                             String state,
                             String zipCode) {
        return new Address(street, number, city, state, zipCode);
    }

    private String validateRequired(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " is required");
        }
        return value.trim();
    }

    private String validateZip(String zip) {
        if (zip == null || !zip.matches("\\d{5}-?\\d{3}")) {
            throw new IllegalArgumentException("Invalid ZIP code");
        }
        return zip.replace("-", "");
    }
}
