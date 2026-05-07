package com.events_service.application;

import com.events_service.domain.Event;
import com.events_service.domain.EventRepository;
import com.events_service.domain.vo.Address;
import com.events_service.infrastructure.messaging.EventProducer;
import com.events_service.adapter.dto.EventRequest;
import org.springframework.stereotype.Service;

@Service
public class CreateEventUseCase {

    private final EventRepository eventRepository;
    private final EventProducer eventProducer;

    public CreateEventUseCase(EventRepository eventRepository, EventProducer eventProducer) {
        this.eventRepository = eventRepository;
        this.eventProducer = eventProducer;
    }

    public Event handleSave(EventRequest request) {

        Address address = Address.of(
                request.address().getStreet(),
                request.address().getNumber(),
                request.address().getCity(),
                request.address().getState(),
                request.address().getZipCode()
        );

        Event event = new Event(
                request.title(),
                request.description(),
                address,
                request.eventDate(),
                request.organizerId()
        );

        Event savedEvent = eventRepository.save(event);

        eventProducer.send("(RABBITMQ) - ✅ Novo evento criado: " + savedEvent.getTitle());
        return savedEvent;
    }
}
