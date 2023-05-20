package school.sptech.conmusicapi.modules.manager.controllers;

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

import java.util.Optional;

@RestController
@RequestMapping("/managers")
@Tag(name = "Managers", description = "Responsible for managing all requests and operations related to manager users")
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @PostMapping
    public ResponseEntity<ManagerDto> create(@RequestBody @Valid CreateManagerDto dto) {
        ManagerDto createdHouse = managerService.create(dto);
        return ResponseEntity.status(201).body(createdHouse);
    }

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

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ManagerDto> getById(@PathVariable Integer id) {
        ManagerDto manager = managerService.getById(id);
        return ResponseEntity.status(200).body(manager);
    }
}
