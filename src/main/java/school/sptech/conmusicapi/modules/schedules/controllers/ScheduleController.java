package school.sptech.conmusicapi.modules.schedules.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import school.sptech.conmusicapi.modules.establishment.dtos.EstablishmentDto;
import school.sptech.conmusicapi.modules.schedules.dtos.CreateScheduleDto;
import school.sptech.conmusicapi.modules.schedules.dtos.ScheduleDto;
import school.sptech.conmusicapi.modules.schedules.services.ScheduleService;
import school.sptech.conmusicapi.shared.utils.collections.DeletionTree;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.conmusicapi.modules.schedules.dtos.CreateScheduleDto;
import school.sptech.conmusicapi.modules.schedules.dtos.ScheduleDto;
import school.sptech.conmusicapi.modules.schedules.services.ScheduleService;
import school.sptech.conmusicapi.shared.exceptions.UnexpectedException;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@Tag(name = "Schedules", description = "Responsible for managing all requests and operations related to schedules")
@SecurityRequirement(name = "Bearer")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private DeletionTree deletionTree;

    @Operation(summary = "Create a schedule", description = "Creates a new schedule for an event")
    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Manager')")
    public ResponseEntity<ScheduleDto> create(
            @PathVariable Integer id,
            @RequestBody @Valid CreateScheduleDto dto
    ) {
        ScheduleDto schedule = scheduleService.create(dto, id);
        return ResponseEntity.status(201).body(schedule);
    }

    @Operation(summary = "List schedules by event", description = "Retrieves a list of schedules for a specific event")
    @GetMapping("/event/{id}")
    public ResponseEntity<List<ScheduleDto>> listByEvent(@PathVariable Integer id) {
        List<ScheduleDto> schedules = scheduleService.listByEventId(id);

        if (schedules.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(schedules);
    }

    @Operation(summary = "List schedules by establishment", description = "Retrieves a list of schedules for a specific establishment")
    @GetMapping("/establishment/{id}")
    public ResponseEntity<List<ScheduleDto>> listByEstablishment(@PathVariable Integer id) {
        List<ScheduleDto> schedules = scheduleService.listByEstablishmentId(id);

        if (schedules.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(schedules);
    }

    @DeleteMapping("/inctivate/{id}")
    @Operation(summary = "inactive schedule by ID", description = "inactive an schedule by its ID")
    public ResponseEntity<ScheduleDto> inactivateById(@PathVariable Integer id){
        ScheduleDto scheduleDto = deletionTree.inactivateSchedule(id);
        return ResponseEntity.status(200).body(scheduleDto);
    }
    @PatchMapping("/activate/{id}")
    @Operation(summary = "Activate an schedule", description = "Activate an existing schedule in the API")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Manager')")
    public ResponseEntity<ScheduleDto> activate(@PathVariable Integer id) {
        ScheduleDto activateSchedule = scheduleService.activateSchedule(id);
        return ResponseEntity.status(200).body(activateSchedule);
    }

    @GetMapping("/inactive")
    @Operation(summary = "Get inactived schedule", description = "Retrieves an inactivad schedule")
    public ResponseEntity<Iterable<ScheduleDto>> inactiveEstablishment(){
        Iterable<ScheduleDto> establishment = scheduleService.findAllInactive();
        return ResponseEntity.status(200).body(establishment);
    }

    
    @Operation(
            summary = "Import schedules",
            description = "Read a csv or txt file and import its data as schedules for events"
    )
    @PostMapping("/import")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<ScheduleDto>> importSchedules(@RequestParam("file") MultipartFile file) throws RuntimeException {
        try {
            List<ScheduleDto> scheduleDtos = scheduleService.importSchedules(file);

            if (scheduleDtos.isEmpty()) {
                return ResponseEntity.status(204).build();
            }

            return ResponseEntity.status(201).body(scheduleDtos);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new UnexpectedException("Unexpected exception: (" + e.getClass().getSimpleName() + ") " + e.getMessage());
        }
    }
}
