package school.sptech.conmusicapi.modules.establishment.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import school.sptech.conmusicapi.modules.establishment.dtos.CreateEstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.dtos.EstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.dtos.UpdateEstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.services.EstablishmentService;

import java.util.List;

@RestController
@RequestMapping("/establishments")
@Tag(name = "Establishments", description = "Responsible for managing all requests and operations related to establishments")
@SecurityRequirement(name = "Bearer")
public class EstablishmentController {
    @Autowired
    private EstablishmentService establishmentService;

    @PostMapping
    @Operation(summary = "Create a new establishment", description = "Registers a new establishment in the API")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Manager')")
    public ResponseEntity<EstablishmentDto> create(@RequestBody @Valid CreateEstablishmentDto dto) {
        EstablishmentDto establishment = establishmentService.create(dto);
        return ResponseEntity.status(201).body(establishment);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an establishment", description = "Updates an existing establishment in the API")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Manager')")
    public ResponseEntity<EstablishmentDto> update(
            @RequestBody @Valid UpdateEstablishmentDto dto,
            @PathVariable Integer id
    ) {
        EstablishmentDto updatedEstablishment = establishmentService.update(dto, id);
        return ResponseEntity.status(200).body(updatedEstablishment);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get establishment by ID", description = "Retrieves an establishment by its ID")
    public ResponseEntity<EstablishmentDto> getById(@PathVariable Integer id) {
        EstablishmentDto establishment = establishmentService.getById(id);
        return ResponseEntity.status(200).body(establishment);
    }

    @Operation(summary = "List establishments by manager ID", description = "Retrieves a list of establishments associated with a manager ID")
    @GetMapping("/manager/{id}")
    public ResponseEntity<List<EstablishmentDto>> listByManagerId(@PathVariable Integer id) {
        List<EstablishmentDto> establishments = establishmentService.getByManagerId(id);

        if (establishments.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(establishments);
    }
}
