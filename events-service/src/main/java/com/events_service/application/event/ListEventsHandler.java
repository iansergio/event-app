package com.events_service.application.event;

import com.events_service.domain.event.Event;
import com.events_service.domain.event.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListEventsHandler {

    private final EventRepository eventRepository;

    public ListEventsHandler(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> handleAll() {
        return eventRepository.findAll();
    }

    public List<Event> handleByTitle(String title) {
        return eventRepository.findAllByTitle(title);
    }

}