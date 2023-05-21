package school.sptech.conmusicapi.modules.show.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.conmusicapi.modules.show.dtos.CreateShowDto;
import school.sptech.conmusicapi.modules.show.dtos.ShowDto;
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
}
