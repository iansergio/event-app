package com.events_service.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository {
    Event save(Event event);
    Optional<Event> findById(UUID id);

    List<Event> findAll();
    List<Event> findAllByTitle(String title);
}
