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
import java.util.Optional;

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
        Optional<ArtistDto> createdArtist = artistService.create(dto);

        if (createdArtist.isEmpty()) {
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.status(201).body(createdArtist.get());
    }
    
    @PutMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ArtistDto> update(@RequestBody @Valid UpdateArtistDto dto){
        Optional<ArtistDto> updatedArtist = artistService.updateArtistDto(dto);

        if (updatedArtist.isEmpty()){
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.status(200).body(updatedArtist.get());
    }
}
