// package com.events_service.application;

// import com.events_service.domain.Event;
// import com.events_service.domain.EventRepository;

// import com.events_service.domain.vo.Status;
// import com.events_service.infrastructure.messaging.EventProducer;
// import org.springframework.stereotype.Service;

// import java.util.UUID;

// @Service
// public class ManageEventStatusUseCase {

//     private final EventRepository eventRepository;
//     private final EventProducer eventProducer;

//     public ManageEventStatusUseCase(EventRepository eventRepository, EventProducer eventProducer) {
//         this.eventRepository = eventRepository;
//         this.eventProducer = eventProducer;
//     }

//     public Event handle(UUID id, Status newStatus, UUID requesterId) {
//         Event event = eventRepository
//                 .findById(id)
//                 .orElseThrow(() -> new RuntimeException("(RABBITMQ) - ❌ Evento não encontrado."));

//         if (!event.getOrganizerId().equals(requesterId)) {
//             throw new RuntimeException("(RABBITMQ) - ❌ Usuário não autorizado a alterar o status do evento.");
//         }

//         event.setStatus(newStatus);
//         Event updatedEvent = eventRepository.save(event);

//         eventProducer.sendNewStatus("(RABBITMQ) - ✅ Status do evento \"" + updatedEvent.getTitle() + "\" alterado para " + newStatus +".");

//         return updatedEvent;
//     }

// }