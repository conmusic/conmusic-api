package school.sptech.conmusicapi.modules.schedules.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.conmusicapi.modules.schedules.dtos.CreateScheduleDto;
import school.sptech.conmusicapi.modules.schedules.dtos.ScheduleDto;
import school.sptech.conmusicapi.modules.schedules.services.ScheduleService;

@RestController
@RequestMapping("/schedules")
@Tag(name = "Schedules", description = "Responsible for managing all requests and operations related to schedules")
@SecurityRequirement(name = "Bearer")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/{id}")
    public ResponseEntity<ScheduleDto> create(
            @PathVariable Integer id,
            @RequestBody @Valid CreateScheduleDto dto
    ) {
        ScheduleDto schedule = scheduleService.create(dto, id);
        return ResponseEntity.status(201).body(schedule);
    }
}
