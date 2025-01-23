package com.stadion.stadion_backend.services;

import com.stadion.stadion_backend.domains.dtos.event.EventFilterRequest;
import com.stadion.stadion_backend.domains.dtos.event.EventResponse;
import com.stadion.stadion_backend.domains.entities.Event;
import com.stadion.stadion_backend.exceptions.EventNotFoundException;
import com.stadion.stadion_backend.mappers.EventMapper;
import com.stadion.stadion_backend.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final FileUploadService fileUploadService;

    public EventResponse createEvent(Event event) {
        Event newEvent = eventRepository.save(event);
        return eventMapper.eventToEventResponse(newEvent);
    }

    public List<EventResponse> findEvents(EventFilterRequest eventFilterRequest) {
        List<Event> events = eventRepository.findByFilter(eventFilterRequest);
        return events.stream().map(eventMapper::eventToEventResponse).toList();
    }

    public EventResponse findEventById(String id) {
        Optional<Event> event = eventRepository.findById(UUID.fromString(id));

        if (event.isEmpty()) {
            throw new EventNotFoundException("Event with id " + id + " was not found");
        }
        return eventMapper.eventToEventResponse(event.get());
    }

    public void uploadImage(MultipartFile file, String id) throws IOException {
        Optional<Event> event = eventRepository.findById(UUID.fromString(id));

        if (event.isEmpty()) {
            throw new EventNotFoundException("Event with id " + id + " was not found");
        }

        String fileName = "events/" + file.getName() + System.currentTimeMillis();
        String imageUrl = fileUploadService.uploadImage(file, fileName);
        event.get().setImageUrl(imageUrl);

        eventRepository.save(event.get());
    }
}
