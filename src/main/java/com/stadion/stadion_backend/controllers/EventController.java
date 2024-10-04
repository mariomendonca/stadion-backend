package com.stadion.stadion_backend.controllers;

import com.stadion.stadion_backend.domains.dtos.event.EventResponse;
import com.stadion.stadion_backend.domains.entities.Event;
import com.stadion.stadion_backend.enums.EventCategory;
import com.stadion.stadion_backend.services.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/events")
@AllArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody Event event) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.createEvent(event));
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getEvents(
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) List<EventCategory> categories,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam Integer page
    ) {
        return ResponseEntity.ok(eventService.findEvents(
                states,
                categories,
                name,
                startDate,
                endDate,
                page
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable String id) {
        return ResponseEntity.ok(eventService.findEventById(id));
    }
}
