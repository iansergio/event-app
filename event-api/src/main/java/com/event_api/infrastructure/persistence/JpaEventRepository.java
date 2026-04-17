package com.event_api.infrastructure.persistence;

import com.event_api.domain.event.Event;
import com.event_api.domain.event.EventRepository;
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
