package school.sptech.conmusicapi.modules.artist.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.conmusicapi.modules.artist.dtos.ArtistDto;
import school.sptech.conmusicapi.modules.artist.dtos.CreateArtistDto;
import school.sptech.conmusicapi.modules.artist.dtos.UpdateArtistDto;
import school.sptech.conmusicapi.modules.artist.services.ArtistService;
import school.sptech.conmusicapi.modules.media.services.StorageService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/artists")
@Tag(name = "Artists", description = "Responsible for managing all requests and operations related to artist users")
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAuthority('Manager') or hasAuthority('Admin')")
    @Operation(summary = "List all artists", description = "Retrieves a list of all artists")
    public ResponseEntity<List<ArtistDto>> findAll() {
        List<ArtistDto> artists = artistService.findAll();

        if (artists.isEmpty()) {
            ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(artists);
    }

    @Operation(summary = "Create an artist", description = "Creates a new artist")
    @PostMapping
    public ResponseEntity<ArtistDto> create(@RequestBody @Valid CreateArtistDto dto) {
        ArtistDto createdArtist = artistService.create(dto);
        return ResponseEntity.status(201).body(createdArtist);
    }

    @Operation(summary = "Update an artist", description = "Updates an existing artist")
    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Admin')")
    public ResponseEntity<ArtistDto> update(
            @RequestBody @Valid UpdateArtistDto dto,
            @PathVariable Integer id
    ){
        ArtistDto updatedArtist = artistService.updateArtistDto(dto, id);
        return ResponseEntity.status(200).body(updatedArtist);
    }

    @Operation(summary = "Get artist by ID", description = "Retrieves an artist by their ID")
    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<ArtistDto> getById(@PathVariable Integer id) {
        ArtistDto artist = artistService.getByArtistId(id);
        return ResponseEntity.status(200).body(artist);
    }

    @Operation(summary = "Register artist genre", description = "Registers a genre for an artist")
    @PatchMapping("/genre/{id}")
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Admin')")
    public ResponseEntity<ArtistDto> registerGenre(
            @PathVariable Integer id,
            @RequestParam String genre
    ){

        ArtistDto artistDto = this.artistService.registerGenreArtist(id, genre);

        return ResponseEntity.created(null).body(artistDto);
    }

    @Operation(summary = "Delete artist genre", description = "Deletes a genre from an artist")
    @DeleteMapping("/genre/{id}")
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Admin')")
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


    @PostMapping("/upload/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<String> uploadFile(
            @PathVariable Integer id,
            @RequestParam("file") MultipartFile file) throws IOException {

        String uploadFile = artistService.uploadFile(file, id);

        return ResponseEntity.ok(uploadFile);
    }

    @GetMapping(value = "/media/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<ByteArrayResource>> getFiles(@PathVariable Integer id){

        List<ByteArrayResource> files = artistService.getFiles(id);

        return ResponseEntity.ok(files);
    }

    @DeleteMapping("/media")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<String> deleteFile(@RequestParam String fileName) {
        String deletedFile = artistService.deleteFile(fileName);
        return ResponseEntity.ok(deletedFile);
    }
}