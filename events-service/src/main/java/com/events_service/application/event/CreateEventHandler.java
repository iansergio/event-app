package com.events_service.application.event;

import com.events_service.domain.event.Event;
import com.events_service.domain.event.EventRepository;
import com.events_service.domain.event.vo.Address;
import com.events_service.infrastructure.messaging.EventProducer;
import com.events_service.interfaces.rest.dto.EventRequest;
import org.springframework.stereotype.Service;

@Service
public class CreateEventHandler {

    private final EventRepository eventRepository;
    private final EventProducer eventProducer;

    public CreateEventHandler(EventRepository eventRepository, EventProducer eventProducer) {
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
