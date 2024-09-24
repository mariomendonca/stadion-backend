package com.stadion.stadion_backend.services;

import com.stadion.stadion_backend.domains.dtos.event.EventResponse;
import com.stadion.stadion_backend.domains.entities.Event;
import com.stadion.stadion_backend.exceptions.EventNotFoundException;
import com.stadion.stadion_backend.mappers.EventMapper;
import com.stadion.stadion_backend.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventResponse createEvent(Event event) {
        Event newEvent = eventRepository.save(event);
        return eventMapper.eventToEventResponse(newEvent);
    }

    public List<EventResponse> findEvents(Integer page) {
        int itemsPerPage = 30;
        Pageable pageable = PageRequest.of(page, itemsPerPage);

        Page<Event> events = eventRepository.findAll(pageable);
        return events.map(eventMapper::eventToEventResponse).toList();
    }

    public EventResponse findEventById(String id) {
        Optional<Event> event = eventRepository.findById(UUID.fromString(id));

        if (event.isEmpty()) {
            throw new EventNotFoundException("Event with id " + id + " was not found");
        }
        return eventMapper.eventToEventResponse(event.get());
    }
}
