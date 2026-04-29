package com.events_service.infrastructure.persistence;

import com.events_service.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataEventJpa extends JpaRepository <Event, UUID> {
    List<Event> findAllByTitle(String title);
}
