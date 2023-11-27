package school.sptech.conmusicapi.modules.manager.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import school.sptech.conmusicapi.modules.manager.dtos.CreateManagerDto;
import school.sptech.conmusicapi.modules.manager.dtos.ManagerDto;
import school.sptech.conmusicapi.modules.manager.dtos.UpdateManagerDto;
import school.sptech.conmusicapi.modules.manager.services.ManagerService;
import school.sptech.conmusicapi.modules.user.dtos.UserKpiDto;
import school.sptech.conmusicapi.modules.user.services.UserService;
import school.sptech.conmusicapi.shared.utils.statistics.GroupDateDoubleSum;
import school.sptech.conmusicapi.shared.utils.statistics.GroupEventsCount;
import school.sptech.conmusicapi.shared.utils.statistics.GroupGenresCount;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/managers")
@Tag(name = "Managers", description = "Responsible for managing all requests and operations related to manager users")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Create a manager", description = "Creates a new manager")
    @PostMapping
    public ResponseEntity<ManagerDto> create(@RequestBody @Valid CreateManagerDto dto) {
        ManagerDto createdHouse = managerService.create(dto);
        return ResponseEntity.status(201).body(createdHouse);
    }

    @Operation(summary = "Update a manager", description = "Updates an existing manager")
    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Manager')")
    public ResponseEntity<ManagerDto> update(
            @RequestBody @Valid UpdateManagerDto dto,
            @PathVariable Integer id
    ){
        ManagerDto updatedHouse = managerService.update(dto, id);
        return ResponseEntity.status(200).body(updatedHouse);
    }

    @Operation(summary = "Get manager by ID", description = "Retrieves a manager by its ID")
    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ManagerDto> getById(@PathVariable Integer id) {
        ManagerDto manager = managerService.getById(id);
        return ResponseEntity.status(200).body(manager);
    }

    @GetMapping("/kpis")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UserKpiDto> getKpis(
            @RequestParam Integer lastDays
    ) {
        Optional<UserKpiDto> managerKpiDto = userService.getManagerOrArtistKpi(lastDays);

        return managerKpiDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/events-chart")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<GroupEventsCount>> getTopEventsCount(
            @RequestParam Integer lastDays
    ) {
        List<GroupEventsCount> mostPopularEvents = managerService.getMostPopularEventsByUserId(lastDays);

        if (mostPopularEvents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(mostPopularEvents);
    }

    @GetMapping("/value-chart")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<GroupDateDoubleSum>> getTotalValueChart(
            @RequestParam Integer lastDays
    ) {
        List<GroupDateDoubleSum> totalValue = userService.getTotalValueChart(lastDays);

        if (totalValue.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(totalValue);
    }
}
