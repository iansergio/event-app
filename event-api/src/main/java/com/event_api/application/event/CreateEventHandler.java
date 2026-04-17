package com.event_api.application.event;

import com.event_api.domain.event.Event;
import com.event_api.domain.event.EventRepository;
import com.event_api.domain.event.vo.Address;
import com.event_api.infrastructure.messaging.EventProducer;
import com.event_api.interfaces.rest.dto.EventRequest;
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
