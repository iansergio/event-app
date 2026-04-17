package com.event_api.application.event;

import com.event_api.domain.event.Event;
import com.event_api.domain.event.EventRepository;
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