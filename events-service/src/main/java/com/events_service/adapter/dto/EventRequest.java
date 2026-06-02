package com.events_service.adapter.dto;

import com.events_service.domain.vo.Address;
import com.events_service.domain.vo.Status;

import java.time.LocalDateTime;

public record EventRequest(
    String title,
    LocalDateTime dateTime,
    Address address,
    Status status
) {

}
