package com.stadion.stadion_backend.services;

import com.stadion.stadion_backend.domains.dtos.event.EventResponse;
import com.stadion.stadion_backend.domains.entities.Event;
import com.stadion.stadion_backend.exceptions.EventNotFoundException;
import com.stadion.stadion_backend.mappers.EventMapper;
import com.stadion.stadion_backend.repositories.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventResponse createEvent(Event event) {
        Event newEvent = eventRepository.save(event);
        return eventMapper.eventToEventResponse(newEvent);
    }

    public List<EventResponse> findEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(eventMapper::eventToEventResponse).toList();
    }

    public EventResponse findEventById(String id) {
        Optional<Event> event = eventRepository.findById(UUID.fromString(id));

        if (event.isEmpty()) {
            throw new EventNotFoundException("Event with id " + id + " was not found");
        }
        return eventMapper.eventToEventResponse(event.get());
    }
}
