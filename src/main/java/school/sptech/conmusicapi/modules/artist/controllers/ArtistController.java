package school.sptech.conmusicapi.modules.artist.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.conmusicapi.modules.artist.dtos.ArtistDto;
import school.sptech.conmusicapi.modules.artist.dtos.CreateArtistDto;
import school.sptech.conmusicapi.modules.artist.dtos.UpdateArtistDto;
import school.sptech.conmusicapi.modules.artist.services.ArtistService;
import school.sptech.conmusicapi.modules.user.dtos.UserKpiDto;
import school.sptech.conmusicapi.modules.user.services.UserService;
import school.sptech.conmusicapi.shared.utils.statistics.GroupDateDoubleSum;
import school.sptech.conmusicapi.shared.utils.statistics.GroupGenresCount;
import school.sptech.conmusicapi.modules.media.dtos.MediaArtistDto;
import school.sptech.conmusicapi.modules.media.services.StorageService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artists")
@Tag(name = "Artists", description = "Responsible for managing all requests and operations related to artist users")
public class ArtistController {
    @Autowired
    private ArtistService artistService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    @SecurityRequirement(name = "Bearer")
    @PreAuthorize("hasAuthority('Artist') or hasAuthority('Admin')")
    @Operation(summary = "Search artists", description = "Searches for artists by name")
    public ResponseEntity<List<ArtistDto>> search(
            @RequestParam String value,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        List<ArtistDto> artists = artistService.search(value, pageable);

        if (artists.isEmpty()) {
            ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(artists);
    }

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

    @GetMapping("/image/perfil/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<MediaArtistDto> getPerfilImage(@PathVariable Integer id){
        return ResponseEntity.ok(artistService.getPerfilImage(id));
    }

    @GetMapping("/images/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<MediaArtistDto>> getImages(@PathVariable Integer id){
        return ResponseEntity.ok(artistService.getImages(id));
    }

    @PostMapping("/upload/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<String> uploadFile(
            @PathVariable Integer id,
            @RequestParam("file") MultipartFile file) throws IOException {

        String uploadFile = artistService.uploadFile(file, id);

        return ResponseEntity.ok(uploadFile);
    }

    @GetMapping(value = "/media/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getFiles(@PathVariable Integer imageId) {
        return ResponseEntity.ok(artistService.getFiles(imageId));
    }

    @DeleteMapping("/media/{imageId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<String> deleteFile(@PathVariable Integer imageId) {
        String deletedFile = artistService.deleteFile(imageId);
        return ResponseEntity.ok(deletedFile);
    }

    @GetMapping("/kpis")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UserKpiDto> getKpis(
            @RequestParam Integer lastDays
    ) {
        Optional<UserKpiDto> artistKpiDto = userService.getManagerOrArtistKpi(lastDays);

        return artistKpiDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/genres-chart")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<GroupGenresCount>> getTopGenresChart(
            @RequestParam Integer lastDays
    ) {
        List<GroupGenresCount> topGenres = artistService.getMostPopularGenresChartByUserId(lastDays);

        if (topGenres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(topGenres);
    }

    @GetMapping("/value-chart")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<GroupDateDoubleSum>> getTotalValueChart(
            @RequestParam Integer lastDays
    ) {
        List<GroupDateDoubleSum> totalValue = userService.getTotalValueChart(lastDays);

        if (totalValue.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(totalValue);
    }
}