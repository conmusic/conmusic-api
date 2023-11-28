package school.sptech.conmusicapi.modules.show.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import school.sptech.conmusicapi.modules.show.dtos.CreateShowDto;
import school.sptech.conmusicapi.modules.show.dtos.ShowDto;
import school.sptech.conmusicapi.modules.show.dtos.ShowRecordDto;
import school.sptech.conmusicapi.modules.show.dtos.UpdateShowDto;
import school.sptech.conmusicapi.modules.show.services.ShowService;
import school.sptech.conmusicapi.modules.show.services.ShowStatisticsService;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;
import school.sptech.conmusicapi.shared.utils.statistics.GroupMonthCount;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;

@RestController
@RequestMapping("/shows")
@Tag(name = "Shows", description = "Responsible for managing all requests and operations related to shows")
@SecurityRequirement(name = "Bearer")
public class ShowController {
    @Autowired
    private ShowService showService;

    @Autowired
    private ShowStatisticsService showStatisticsService;

    @Operation(summary = "Create a show", description = "Creates a new show")
    @PostMapping
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<ShowDto> create(@RequestBody @Valid CreateShowDto dto) {
        ShowDto show = showService.create(dto);
        return ResponseEntity.status(201).body(show);
    }

    @Operation(summary = "Update a show", description = "Updates an existing show")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<ShowDto> update(
            @PathVariable Integer id,
            @RequestBody @Valid UpdateShowDto dto
    ) {
        ShowDto show = showService.update(id, dto);
        return ResponseEntity.status(200).body(show);
    }
    @Operation(summary = "Get show by ID", description = "Retrieves a show by its ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<ShowDto> getById(@PathVariable Integer id) {
        ShowDto show = showService.getById(id);
        return ResponseEntity.status(200).body(show);
    }

    @Operation(summary = "List show changes", description = "Retrieves a list of all changes for a show")
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

    @Operation(summary = "List all show proposals", description = "Retrieves a list of all show proposals")
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

    @Operation(summary = "Start negotiation", description = "Starts a negotiation for a show proposal")
    @PatchMapping("/proposals/{id}")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<ShowDto> startNegotiation(@PathVariable Integer id) {
        ShowDto showDto = showService.acceptProposal(id);
        return ResponseEntity.status(200).body(showDto);
    }

    @Operation(summary = "Reject proposal", description = "Rejects a show proposal")
    @DeleteMapping("/proposals/{id}")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<Void> rejectProposal(@PathVariable Integer id) {
        showService.rejectProposal(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "List all negotiations", description = "Retrieves a list of all show negotiations")
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
                        ShowStatusEnum.MANAGER_CANCELED,
                        ShowStatusEnum.CONCLUDED
                ));

        if (showDtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(showDtos);
    }

    @Operation(summary = "Accept terms of negotiation", description = "Accepts the terms of a show negotiation")
    @PatchMapping("/negotiations/{id}")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<ShowDto> acceptTermsOfNegotiation(@PathVariable Integer id) {
        ShowDto showDto = showService.acceptTermsOfNegotiation(id);
        return ResponseEntity.status(200).body(showDto);
    }

    @Operation(summary = "Withdraw from negotiation", description = "Withdraws from a show negotiation")
    @DeleteMapping("/negotiations/{id}")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<Void> withdrawFromNegotiation(@PathVariable Integer id) {
        showService.withdrawNegotiation(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "List all confirmed shows", description = "Retrieves a list of all confirmed shows")
    @GetMapping("/confirmed")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<List<ShowDto>> listAllConfirmed() {
        List<ShowDto> showDtos = showService.listByStatus(EnumSet.of(ShowStatusEnum.CONFIRMED));

        if (showDtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(showDtos);
    }

    @Operation(summary = "Conclude show", description = "Concludes a show")
    @PatchMapping("/confirmed/{id}")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<ShowDto> concludeShow(@PathVariable Integer id) {
        ShowDto showDto = showService.concludeShow(id);
        return ResponseEntity.status(200).body(showDto);
    }

    @Operation(summary = "Cancel show", description = "Cancels a show")
    @DeleteMapping("/confirmed/{id}")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<Void> cancel(@PathVariable Integer id) {
        showService.cancelShow(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(
            summary = "Count confirmed shows by month",
            description = "Retrieves the count of confirmed shows grouped by month in a specified date interval"
    )
    @GetMapping("/statistics/count-confirmed-by-month")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Manager')")
    public ResponseEntity<List<GroupMonthCount>> countConfirmedShowsByInDateIntervalGroupByMonth(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate
    ) {
        List<GroupMonthCount> result = showStatisticsService.countConfirmedShowsByInDateIntervalGroupByMonth(startDate, endDate);

        if (result.isEmpty()) {
            ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(result);
    }
}
