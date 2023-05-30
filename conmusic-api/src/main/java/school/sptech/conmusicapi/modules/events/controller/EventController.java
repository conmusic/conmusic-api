package school.sptech.conmusicapi.modules.events.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import school.sptech.conmusicapi.modules.events.dtos.CreateEventDto;
import school.sptech.conmusicapi.modules.events.dtos.EventDto;
import school.sptech.conmusicapi.modules.events.services.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@Tag(name = "Events", description = "Responsible for managing all requests and operations related to events")
@SecurityRequirement(name = "Bearer")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAuthority('Manager')")
    public ResponseEntity<EventDto> create(@RequestBody @Valid CreateEventDto dto) {
        EventDto event = eventService.create(dto);
        return ResponseEntity.status(201).body(event);
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<EventDto>> listaAll() {
        List<EventDto> events = eventService.listAll();

        if (events.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(events);
    }

    @GetMapping("/establishment/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<EventDto>> listByEstablishment(@PathVariable Integer id) {
        List<EventDto> events = eventService.listAllByEstablishmentId(id);

        if (events.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(events);
    }

    @GetMapping("/available")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<EventDto>> listAvailable(
            @RequestParam LocalDateTime date
    ) {
        List<EventDto> events = eventService.listAllAvailable(date);

        if (events.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(events);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EventDto> getById(@PathVariable Integer id) {
        EventDto event = eventService.getById(id);
        return ResponseEntity.status(200).body(event);
    }
}
