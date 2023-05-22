package school.sptech.conmusicapi.modules.show.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import school.sptech.conmusicapi.modules.show.dtos.CreateShowDto;
import school.sptech.conmusicapi.modules.show.dtos.ShowDto;
import school.sptech.conmusicapi.modules.show.dtos.UpdateShowDto;
import school.sptech.conmusicapi.modules.show.services.ShowService;

@RestController
@RequestMapping("/shows")
@Tag(name = "Shows", description = "Responsible for managing all requests and operations related to shows")
@SecurityRequirement(name = "Bearer")
public class ShowController {
    @Autowired
    private ShowService showService;

    @PostMapping
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<ShowDto> create(@RequestBody @Valid CreateShowDto dto) {
        ShowDto show = showService.create(dto);
        return ResponseEntity.status(201).body(show);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<ShowDto> update(
            @PathVariable Integer id,
            @RequestBody @Valid UpdateShowDto dto
    ) {
        ShowDto show = showService.update(id, dto);
        return ResponseEntity.status(200).body(show);
    }

    @PatchMapping("/proposals/{id}")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<ShowDto> startNegotiation(
            @PathVariable Integer id
    ) {
        final String NEW_STATUS = "NEGOTIATION";
        ShowDto showDto = showService.updateStatus(id, NEW_STATUS);
        return ResponseEntity.status(200).body(showDto);
    }

    @DeleteMapping("/proposals/{id}")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<Void> rejectProposal(
            @PathVariable Integer id
    ) {
        final String NEW_STATUS = "REJECTED";
        showService.updateStatus(id, NEW_STATUS);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/negotiations/{id}")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<ShowDto> acceptTermsOfNegotiation(
            @PathVariable Integer id
    ) {
        final String NEW_STATUS = "ACCEPTED";
        ShowDto showDto = showService.updateStatus(id, NEW_STATUS);
        return ResponseEntity.status(200).body(showDto);
    }

    @DeleteMapping("/negotiations/{id}")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<Void> withdrawFromNegotiation(
            @PathVariable Integer id
    ) {
        final String NEW_STATUS = "WITHDRAW";
        showService.updateStatus(id, NEW_STATUS);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/confirmed/{id}")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<ShowDto> concludeShow(
            @PathVariable Integer id
    ) {
        final String NEW_STATUS = "CONCLUDED";
        ShowDto showDto = showService.updateStatus(id, NEW_STATUS);
        return ResponseEntity.status(200).body(showDto);
    }

    @DeleteMapping("/confirmed/{id}")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<Void> cancel(
            @PathVariable Integer id
    ) {
        final String NEW_STATUS = "CANCELED";
        showService.updateStatus(id, NEW_STATUS);
        return ResponseEntity.status(204).build();
    }
}
