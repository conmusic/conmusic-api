package school.sptech.conmusicapi.modules.artist.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.conmusicapi.modules.artist.dtos.ArtistDto;
import school.sptech.conmusicapi.modules.artist.dtos.CreateArtistDto;
import school.sptech.conmusicapi.modules.artist.dtos.UpdateArtistDto;
import school.sptech.conmusicapi.modules.artist.services.ArtistService;

import java.util.List;

@RestController
@RequestMapping("/artists")
@Tag(name = "Artists", description = "Responsible for managing all requests and operations related to artist users")
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<ArtistDto>> findAll() {
        List<ArtistDto> artists = artistService.findAll();

        if (artists.isEmpty()) {
            ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(artists);
    }

    @PostMapping
    public ResponseEntity<ArtistDto> create(@RequestBody @Valid CreateArtistDto dto) {
        ArtistDto createdArtist = artistService.create(dto);
        return ResponseEntity.status(201).body(createdArtist);
    }
    
    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ArtistDto> update(
            @RequestBody @Valid UpdateArtistDto dto,
            @PathVariable Integer id
    ){
        ArtistDto updatedArtist = artistService.updateArtistDto(dto, id);
        return ResponseEntity.status(200).body(updatedArtist);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ArtistDto> getById(@PathVariable Integer id) {
        ArtistDto artist = artistService.getByArtistId(id);
        return ResponseEntity.status(200).body(artist);
    }

    @PutMapping("/genre/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ArtistDto> registerGenre(
            @PathVariable Integer id,
            @RequestParam String genre
    ){

        ArtistDto artistDto = this.artistService.registerGenreArtist(id, genre);

        return ResponseEntity.created(null).body(artistDto);
    }

    @PutMapping("/delete-genre/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> deleteGenre(
            @PathVariable Integer id,
            @RequestParam String genre
    ){
        int deleted = this.artistService.deleteGenreArtist(id, genre);

        if (deleted > 0){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
