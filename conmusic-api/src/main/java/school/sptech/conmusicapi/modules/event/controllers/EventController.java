package school.sptech.conmusicapi.modules.event.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.conmusicapi.modules.event.dtos.CreateEventDto;
import school.sptech.conmusicapi.modules.event.dtos.EventDto;
import school.sptech.conmusicapi.modules.event.entities.Event;
import school.sptech.conmusicapi.modules.event.services.EventService;
import school.sptech.conmusicapi.modules.event.repositories.IEventRepository;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private IEventRepository eventRepository;
    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<EventDto> create(@RequestBody @Valid CreateEventDto dto) {
        Optional<EventDto> createdEvent = eventService.create(dto);

        if (createdEvent.isEmpty()) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(201).body(createdEvent.get());
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        if (events.isEmpty()) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(201).body(events);
    }

}
