package com.stadion.stadion_backend.repositories;

import com.stadion.stadion_backend.domains.entities.Event;
import com.stadion.stadion_backend.enums.EventCategory;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomEventRepository {
    List<Event> findByFilter(
            List<String> states,
            List<EventCategory> categories,
            String name,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    );
}
