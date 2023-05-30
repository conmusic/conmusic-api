package school.sptech.conmusicapi.modules.schedules.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import school.sptech.conmusicapi.modules.schedules.dtos.CreateScheduleDto;
import school.sptech.conmusicapi.modules.schedules.dtos.ScheduleDto;
import school.sptech.conmusicapi.modules.schedules.services.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@Tag(name = "Schedules", description = "Responsible for managing all requests and operations related to schedules")
@SecurityRequirement(name = "Bearer")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Manager')")
    public ResponseEntity<ScheduleDto> create(
            @PathVariable Integer id,
            @RequestBody @Valid CreateScheduleDto dto
    ) {
        ScheduleDto schedule = scheduleService.create(dto, id);
        return ResponseEntity.status(201).body(schedule);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<List<ScheduleDto>> listByEvent(@PathVariable Integer id) {
        List<ScheduleDto> schedules = scheduleService.listByEventId(id);

        if (schedules.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(schedules);
    }

    @GetMapping("/establishment/{id}")
    public ResponseEntity<List<ScheduleDto>> listByEstablishment(@PathVariable Integer id) {
        List<ScheduleDto> schedules = scheduleService.listByEstablishmentId(id);

        if (schedules.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(schedules);
    }
}
