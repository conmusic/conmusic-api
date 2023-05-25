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
import school.sptech.conmusicapi.modules.show.dtos.ShowRecordDto;
import school.sptech.conmusicapi.modules.show.dtos.UpdateShowDto;
import school.sptech.conmusicapi.modules.show.services.ShowService;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;

import java.util.EnumSet;
import java.util.List;

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

    @GetMapping("/history/{id}")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<List<ShowRecordDto>> listAllChangesByShowId(
            @PathVariable Integer id
    ) {
        List<ShowRecordDto> history = showService.listAllChangesByShowId(id);

        if (history.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(history);
    }

    @GetMapping("/proposals")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<List<ShowDto>> listAllProposals() {
        List<ShowDto> showDtos = showService
                .listByStatus(EnumSet.of(ShowStatusEnum.ARTIST_PROPOSAL, ShowStatusEnum.MANAGER_PROPOSAL));

        if (showDtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(showDtos);
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

    @GetMapping("/negotiations")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<List<ShowDto>> listAllNegotiations() {
        List<ShowDto> showDtos = showService
                .listByStatus(EnumSet.of(
                        ShowStatusEnum.NEGOTIATION,
                        ShowStatusEnum.ARTIST_ACCEPTED,
                        ShowStatusEnum.MANAGER_ACCEPTED,
                        ShowStatusEnum.CONFIRMED,
                        ShowStatusEnum.ARTIST_WITHDRAW,
                        ShowStatusEnum.MANAGER_WITHDRAW,
                        ShowStatusEnum.ARTIST_CANCELED,
                        ShowStatusEnum.MANAGER_CANCELED
                ));

        if (showDtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(showDtos);
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
