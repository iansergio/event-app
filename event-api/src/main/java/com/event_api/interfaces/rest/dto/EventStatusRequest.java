package com.event_api.interfaces.rest.dto;

import com.event_api.domain.event.vo.Status;

import java.util.UUID;

public record EventStatusRequest (
        Status status,
        UUID requesterId
) {

}
