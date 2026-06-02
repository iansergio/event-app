package com.events_service.application;

import com.events_service.domain.Event;
import com.events_service.domain.EventRepository;
import com.events_service.adapter.dto.EventRequest;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateEventUseCase {

    private final EventRepository repository;

    public CreateEventUseCase(EventRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Event execute(EventRequest request, UUID organizerId) {

        Event newEvent = new Event(
            request.title(), 
            request.dateTime(),
            request.address(), 
            organizerId
        );

        return repository.save(newEvent);

        // producer.send(newEvent.getId());
    }
}
