package com.event_api.interfaces.rest.dto;

import com.event_api.domain.event.vo.Address;
import com.event_api.domain.event.vo.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record EventResponse(
        UUID eventId,
        String title,
        String description,
        Address address,
        Status status,
        LocalDate eventDate,
        UUID organizerId,
        LocalDateTime createdAt
) {

}
