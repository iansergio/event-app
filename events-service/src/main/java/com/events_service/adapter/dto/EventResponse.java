package com.events_service.adapter.dto;

import com.events_service.domain.vo.Address;
import com.events_service.domain.vo.Status;

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
