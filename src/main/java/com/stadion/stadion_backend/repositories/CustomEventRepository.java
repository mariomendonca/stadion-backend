package com.stadion.stadion_backend.repositories;

import com.stadion.stadion_backend.domains.dtos.event.EventFilterRequest;
import com.stadion.stadion_backend.domains.entities.Event;

import java.util.List;

public interface CustomEventRepository {
    List<Event> findByFilter(EventFilterRequest eventFilterRequest);
}
