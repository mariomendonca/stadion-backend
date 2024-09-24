package com.stadion.stadion_backend.domains.dtos.event;

import com.stadion.stadion_backend.enums.EventCategory;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class EventResponse {
    private UUID id;
    private String name;
    private String description;
    private String imageUrl;
    private String redirectUrl;
    private String country;
    private String state;
    private String city;
    private String address;
    private EventCategory category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
}
