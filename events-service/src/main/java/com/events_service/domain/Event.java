package com.events_service.domain;

import com.events_service.domain.vo.Address;
import com.events_service.domain.vo.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "events", schema = "event_app")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;

    @Column(name = "event_date")
    private LocalDateTime dateTime;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Status status;
    private UUID organizerId;

    public Event(String title, LocalDateTime dateTime, Address address, UUID organizerId) {
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("A data do evento não pode ser no passado");
        }
        this.title = title;
        this.dateTime = dateTime;
        this.address = address;
        this.status = Status.DRAFT;
        this.organizerId = organizerId;
    }
}
