package com.events_service.application;

import com.events_service.domain.Event;
import com.events_service.domain.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListEventsUseCase {

	private final EventRepository eventRepository;

	public ListEventsUseCase(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}

	public List<Event> handleAll() {
		return eventRepository.findAll();
	}

	public List<Event> handleByTitle(String title) {
		return eventRepository.findAllByTitle(title);
	}

}