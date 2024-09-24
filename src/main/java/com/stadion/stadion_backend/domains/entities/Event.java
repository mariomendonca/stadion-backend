package com.stadion.stadion_backend.domains.entities;

import com.stadion.stadion_backend.enums.EventCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private String imageUrl;
    private String redirectUrl;
    @Enumerated(EnumType.STRING)
    private EventCategory category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
