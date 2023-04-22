package school.sptech.conmusicapi.modules.artist.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import school.sptech.conmusicapi.modules.artist.dtos.ArtistDto;
import school.sptech.conmusicapi.modules.artist.dtos.CreateArtistDto;
import school.sptech.conmusicapi.modules.artist.services.ArtistService;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artists")
public class ArtistController {
    @Autowired
    private ArtistService artistService;


    @GetMapping
    public ResponseEntity<List<ArtistDto>> findAll() {
        List<ArtistDto> artists = artistService.findAll();
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

}
