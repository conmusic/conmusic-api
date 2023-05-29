package school.sptech.conmusicapi.modules.genre.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import school.sptech.conmusicapi.modules.genre.dto.DisplayingGenreDto;
import school.sptech.conmusicapi.modules.genre.dto.RegisterGenreDto;
import school.sptech.conmusicapi.modules.genre.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genres")
@Tag(name = "Genres", description = "This endpoint is responsible for creating music genres in the API")
@SecurityRequirement(name = "Bearer")
public class GenreController {

    @Autowired
    GenreService genreService;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<DisplayingGenreDto> registerGender(@RequestBody @Valid RegisterGenreDto registerGenreDto){
        return ResponseEntity.created(null).body(genreService.register(registerGenreDto));
    }

    @GetMapping
    public ResponseEntity<List<DisplayingGenreDto>> listAll() {
        List<DisplayingGenreDto> genres = genreService.list();

        if (genres.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(genres);
    }
}
