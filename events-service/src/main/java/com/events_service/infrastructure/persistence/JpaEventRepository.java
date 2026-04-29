package com.events_service.infrastructure.persistence;

import com.events_service.domain.event.Event;
import com.events_service.domain.event.EventRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaEventRepository implements EventRepository {
    private final SpringDataEventJpa jpa;

    public JpaEventRepository(SpringDataEventJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public Event save(Event event) {
        return jpa.save(event);
    }

    public Optional<Event> findById(UUID id) {
        return jpa.findById(id);
    }

    public List<Event> findAll() {
        return jpa.findAll();
    }

    public List<Event> findAllByTitle(String title) {
        return jpa.findAllByTitle(title);
    }
}
