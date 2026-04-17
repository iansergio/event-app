package com.event_api.domain.event;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository {
    Event save(Event event);
    Optional<Event> findById(UUID id);
    List<Event> findAll();
    List<Event> findAllByTitle(String title);
}
