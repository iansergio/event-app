package com.events_service.interfaces.rest.dto;

import com.events_service.domain.event.vo.Status;

import java.util.UUID;

public record EventStatusRequest (
        Status status,
        UUID requesterId
) {

}
