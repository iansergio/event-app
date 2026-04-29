package com.events_service.interfaces.rest.dto;

import com.events_service.domain.event.vo.Address;
import com.events_service.domain.event.vo.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.UUID;

public record EventRequest(
        String title,
        String description,
        Address address,
        Status status,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate eventDate,
        UUID organizerId
) {

}
