package com.event_api.infrastructure.persistence;

import com.event_api.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataEventJpa extends JpaRepository <Event, UUID> {
    List<Event> findAllByTitle(String title);
}
