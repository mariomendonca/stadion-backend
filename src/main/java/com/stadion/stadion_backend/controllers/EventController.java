package com.stadion.stadion_backend.controllers;

import com.stadion.stadion_backend.domains.dtos.event.EventFilterRequest;
import com.stadion.stadion_backend.domains.dtos.event.EventResponse;
import com.stadion.stadion_backend.domains.entities.Event;
import com.stadion.stadion_backend.services.EventService;
import com.stadion.stadion_backend.services.FileUploadService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<List<EventResponse>> getEvents(EventFilterRequest eventFilterRequest) {
        return ResponseEntity.ok(eventService.findEvents(eventFilterRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable String id) {
        return ResponseEntity.ok(eventService.findEventById(id));
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(@PathVariable String id, @RequestParam("file") MultipartFile file) throws IOException {
        eventService.uploadImage(file, id);
        return ResponseEntity.ok("Image uploaded successfully");
    }
}
