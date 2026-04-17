package com.event_api.interfaces.rest;

import com.event_api.application.event.CreateEventHandler;
import com.event_api.application.event.ListEventsHandler;
import com.event_api.application.event.ManageEventStatusHandler;
import com.event_api.domain.event.Event;
import com.event_api.interfaces.rest.dto.EventRequest;
import com.event_api.interfaces.rest.dto.EventResponse;
import com.event_api.interfaces.rest.dto.EventStatusRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    private final ListEventsHandler listEventsHandler;
    private final CreateEventHandler createEventHandler;
    private final ManageEventStatusHandler manageEventStatusHandler;

    public EventController(ListEventsHandler listEventsHandler, CreateEventHandler createEventHandler, ManageEventStatusHandler manageEventStatusHandler) {
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
    public ResponseEntity<EventResponse> create(@Valid @RequestBody EventRequest request) {
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
