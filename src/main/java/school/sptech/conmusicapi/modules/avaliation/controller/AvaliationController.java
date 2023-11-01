package school.sptech.conmusicapi.modules.avaliation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import school.sptech.conmusicapi.modules.avaliation.dtos.AvaliationDto;
import school.sptech.conmusicapi.modules.avaliation.dtos.CreateAvaliationDto;
import school.sptech.conmusicapi.modules.avaliation.services.AvaliationService;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/avaliation")
@Tag(name = "Avaliation", description = "Responsible for managing all requests about avaliations of establishments")
@SecurityRequirement(name = "Bearer")
public class AvaliationController {

@Autowired
private AvaliationService avaliationService;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Register a new Avaliation", description = "Register a new avaliation")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Artist')")
    public ResponseEntity<AvaliationDto> create(@RequestBody @Valid CreateAvaliationDto dto) {
        AvaliationDto aval = avaliationService.create(dto);
        return ResponseEntity.status(201).body(aval);
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "List all avaliations", description = "Retrieves a list of all avaliations in the API")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Artist')")
    public ResponseEntity<List<AvaliationDto>> listaAll() {
        List<AvaliationDto> avaliations = avaliationService.listAll();

        if (avaliations.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(avaliations);
    }

    @GetMapping("/show/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "List avaliations by shows", description = "Retrieves a rating of specific show")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Artist')")
    public ResponseEntity<List<AvaliationDto>> listByShow(@PathVariable Integer id) {
        List<AvaliationDto> aval = avaliationService.listByShowId(id);
        if (aval.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(aval);
    }
    @GetMapping("/artist/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "List avaliations by artist", description = "Retrieves a rating that a specific artist gave")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Artist')")
    public ResponseEntity<List<AvaliationDto>> listByArtist(@PathVariable Integer id) {
        List<AvaliationDto> aval = avaliationService.listByArtistId(id);
        if (aval.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(aval);
    }
}
