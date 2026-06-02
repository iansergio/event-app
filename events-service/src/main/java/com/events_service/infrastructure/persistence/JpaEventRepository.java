package com.events_service.infrastructure.persistence;

import com.events_service.domain.EventRepository;
import com.events_service.domain.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaEventRepository extends JpaRepository<Event, UUID>, EventRepository {
}
