package com.event_api.application.event;

import com.event_api.domain.event.Event;
import com.event_api.domain.event.EventRepository;

import com.event_api.domain.event.vo.Status;
import com.event_api.infrastructure.messaging.EventProducer;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ManageEventStatusHandler {

    private final EventRepository eventRepository;
    private final EventProducer eventProducer;

    public ManageEventStatusHandler(EventRepository eventRepository, EventProducer eventProducer) {
        this.eventRepository = eventRepository;
        this.eventProducer = eventProducer;
    }

    public Event handle(UUID id, Status newStatus, UUID requesterId) {
        Event event = eventRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("(RABBITMQ) - ❌ Evento não encontrado."));

        if (!event.getOrganizerId().equals(requesterId)) {
            throw new RuntimeException("(RABBITMQ) - ❌ Usuário não autorizado a alterar o status do evento.");
        }

        event.setStatus(newStatus);
        Event updatedEvent = eventRepository.save(event);

        eventProducer.sendNewStatus("(RABBITMQ) - ✅ Status do evento \"" + updatedEvent.getTitle() + "\" alterado para " + newStatus +".");

        return updatedEvent;
    }

}