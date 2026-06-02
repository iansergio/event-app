package com.events_service.adapter;

import com.events_service.application.CreateEventUseCase;
import com.events_service.application.ListEventsUseCase;
import com.events_service.domain.Event;
import com.events_service.adapter.dto.EventRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final CreateEventUseCase useCase;
    private final ListEventsUseCase listEventsUseCase;
    
    public EventController(CreateEventUseCase useCase, ListEventsUseCase listEventsUseCase) {
        this.useCase = useCase;
        this.listEventsUseCase = listEventsUseCase;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody EventRequest request, Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario nao autenticado");
        }

        UUID organizerId = UUID.fromString(authentication.getPrincipal().toString());

        Event newEvent = useCase.execute(request, organizerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<Event>> listAll(Authentication authentication) {
        return ResponseEntity.ok(listEventsUseCase.handleAll());
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{eventTitle}")
    public ResponseEntity<List<Event>> findAllByTitle(@PathVariable String eventTitle, Authentication authentication) {
        List<Event> events = listEventsUseCase.handleByTitle(eventTitle);

        if (events.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(events);
    }
}
