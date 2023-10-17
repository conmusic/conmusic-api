package school.sptech.conmusicapi.modules.events.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import school.sptech.conmusicapi.modules.events.dtos.CreateEventDto;
import school.sptech.conmusicapi.modules.events.dtos.EventDto;
import school.sptech.conmusicapi.modules.events.services.EventService;
import school.sptech.conmusicapi.shared.utils.datafiles.DataFilesEnum;

import java.io.IOException;
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
    @Operation(summary = "Register a new event", description = "Registers a new music event in the API")
    public ResponseEntity<EventDto> create(@RequestBody @Valid CreateEventDto dto) {
        EventDto event = eventService.create(dto);
        return ResponseEntity.status(201).body(event);
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "List all events", description = "Retrieves a list of all music events in the API")
    public ResponseEntity<List<EventDto>> listaAll() {
        List<EventDto> events = eventService.listAll();

        if (events.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(events);
    }

    @GetMapping("/establishment/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "List events by establishment", description = "Retrieves a list of music events associated with a specific establishment")
    public ResponseEntity<List<EventDto>> listByEstablishment(@PathVariable Integer id) {
        List<EventDto> events = eventService.listAllByEstablishmentId(id);

        if (events.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(events);
    }

    @GetMapping("/available")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "List available events", description = "Retrieves a list of available music events based on a specified date")
    public ResponseEntity<List<EventDto>> listAvailable(
            @RequestParam LocalDateTime date
    ) {
        List<EventDto> events = eventService.listAllAvailable(date);

        if (events.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(events);
    }
    @Operation(summary = "Get event by ID", description = "Retrieves a music event by its ID")
    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EventDto> getById(@PathVariable Integer id) {
        EventDto event = eventService.getById(id);
        return ResponseEntity.status(200).body(event);
    }

    @GetMapping("/export/lineup/{id}")
    public ResponseEntity<byte[]> exportEventLineup(
            @PathVariable Integer id,
            @RequestParam String fileFormat
    ) throws IOException {
        DataFilesEnum selectedFormat = DataFilesEnum.getByName(fileFormat.toUpperCase());

        String content = eventService.exportEventLineup(id, selectedFormat);
        byte[] fileContent = content.getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(fileContent.length);
        headers.setContentType(selectedFormat.getContentType());
        headers.setContentDispositionFormData("attachment", String.format("EVENTLINEUP_%d%s", id, selectedFormat.getExtension()));

        return ResponseEntity
                .status(200)
                .headers(headers)
                .body(fileContent);
    }
}
