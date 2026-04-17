package com.event_api.domain.event;

import com.event_api.domain.event.vo.Address;
import com.event_api.domain.event.vo.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID eventId;

    private String title;
    private String description;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate eventDate;

    private UUID organizerId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Event(String title, String description, Address address, LocalDate eventDate, UUID organizerId) {
        this.title = title;
        this.description = description;
        this.address = address;
        this.status = Status.PLANNED;
        this.eventDate = eventDate;
        this.createdAt = LocalDateTime.now();
        this.organizerId = organizerId;
    }
}
