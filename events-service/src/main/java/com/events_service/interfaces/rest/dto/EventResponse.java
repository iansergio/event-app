package com.events_service.interfaces.rest.dto;

import com.events_service.domain.event.vo.Address;
import com.events_service.domain.event.vo.Status;

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
