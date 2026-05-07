package com.events_service.adapter;

import com.events_service.application.CreateEventUseCase;
import com.events_service.application.ListEventsUseCase;
import com.events_service.application.ManageEventStatusUseCase;
import com.events_service.domain.Event;
import com.events_service.adapter.dto.EventRequest;
import com.events_service.adapter.dto.EventResponse;
import com.events_service.adapter.dto.EventStatusRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    private final ListEventsUseCase listEventsHandler;
    private final CreateEventUseCase createEventHandler;
    private final ManageEventStatusUseCase manageEventStatusHandler;

    public EventController(ListEventsUseCase listEventsHandler, CreateEventUseCase createEventHandler, ManageEventStatusUseCase manageEventStatusHandler) {
        this.listEventsHandler = listEventsHandler;
        this.createEventHandler = createEventHandler;
        this.manageEventStatusHandler = manageEventStatusHandler;
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> listAll() {
        List<Event> events = listEventsHandler.handleAll();

        List<EventResponse> responses = events
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{eventTitle}")
    public ResponseEntity<?> findAllByTitle(@PathVariable String eventTitle) {
        List<EventResponse> responses = listEventsHandler.handleByTitle(eventTitle)
                .stream()
                .map(this::toResponse)
                .toList();

        if (responses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Nenhum evento encontrado com o título '" + eventTitle + "'");
        }

        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<EventResponse> create( @RequestBody EventRequest request) {
        Event event = createEventHandler.handleSave(request);
        URI location = URI.create("/events/" + event.getEventId());

        EventResponse response = toResponse(event);

        return ResponseEntity
                .created(location)
                .body(response);
    }

    private EventResponse toResponse(Event event) {
        return new EventResponse(
                event.getEventId(),
                event.getTitle(),
                event.getDescription(),
                event.getAddress(),
                event.getStatus(),
                event.getEventDate(),
                event.getOrganizerId(),
                event.getCreatedAt()
        );
    }

    @PatchMapping("/{eventId}/status")
    public ResponseEntity<EventResponse> updateStatus(
            @PathVariable UUID eventId,
            @RequestBody EventStatusRequest request ) {

        Event updatedEvent = manageEventStatusHandler.handle(
                eventId,
                request.status(),
                request.requesterId()
        );

        EventResponse response = toResponse(updatedEvent);
        return ResponseEntity.ok(response);
    }
}
