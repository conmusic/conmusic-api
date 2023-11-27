package school.sptech.conmusicapi.modules.establishment.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.conmusicapi.modules.establishment.dtos.CreateEstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.dtos.EstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.dtos.InactiveEstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.dtos.UpdateEstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.services.EstablishmentService;
import school.sptech.conmusicapi.modules.media.dtos.MediaArtistDto;
import school.sptech.conmusicapi.modules.media.dtos.MediaEstablishmentDto;
import school.sptech.conmusicapi.modules.events.dtos.EventDto;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.shared.utils.collections.DeletionTree;
import school.sptech.conmusicapi.shared.utils.collections.NodeGen;
import school.sptech.conmusicapi.shared.utils.collections.TypeForDeletionEnum;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/establishments")
@Tag(name = "Establishments", description = "Responsible for managing all requests and operations related to establishments")
@SecurityRequirement(name = "Bearer")
public class EstablishmentController {
    @Autowired
    private EstablishmentService establishmentService;
    @Autowired
    private DeletionTree deletionTree;

    @PostMapping
    @Operation(summary = "Create a new establishment", description = "Registers a new establishment in the API")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Manager')")
    public ResponseEntity<EstablishmentDto> create(@RequestBody @Valid CreateEstablishmentDto dto) {
        EstablishmentDto establishment = establishmentService.create(dto);
        return ResponseEntity.status(201).body(establishment);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Manager')")
    @Operation(summary = "Search establishments", description = "Searches for establishments by name")
    public ResponseEntity<List<EstablishmentDto>> search(
            @RequestParam String value,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        List<EstablishmentDto> establishments = establishmentService.search(value, pageable);

        if (establishments.isEmpty()) {
            ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(establishments);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an establishment", description = "Updates an existing establishment in the API")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Manager')")
    public ResponseEntity<EstablishmentDto> update(
            @RequestBody @Valid UpdateEstablishmentDto dto,
            @PathVariable Integer id
    ) {
        EstablishmentDto updatedEstablishment = establishmentService.update(dto, id);
        return ResponseEntity.status(200).body(updatedEstablishment);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get establishment by ID", description = "Retrieves an establishment by its ID")
    public ResponseEntity<EstablishmentDto> getById(@PathVariable Integer id) {
        EstablishmentDto establishment = establishmentService.getById(id);
        return ResponseEntity.status(200).body(establishment);
    }

    @DeleteMapping("/inctivate/{id}")
    @Operation(summary = "inactive establishment by ID", description = "inactive an establishment by its ID")
    public ResponseEntity<EstablishmentDto> inactivateById(@PathVariable Integer id){
        EstablishmentDto establishmentDto = establishmentService.getById(id);
        deletionTree.createRoot(establishmentService.getById(id), TypeForDeletionEnum.ESTABLISHMENT);
        deletionTree.insert(deletionTree.getRoot());
        deletionTree.deletionSequenceOnTree(deletionTree.getRoot());
        return ResponseEntity.status(200).body(establishmentDto);
    }
    @PatchMapping("/activate/{id}")
    @Operation(summary = "Activate an establishment", description = "Activate an existing establishment in the API")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Manager')")
    public ResponseEntity<EstablishmentDto> activate(@PathVariable Integer id) {
        EstablishmentDto activateEstablishment = establishmentService.activateEstablishment(id);
        return ResponseEntity.status(200).body(activateEstablishment);
    }

    @GetMapping("/inactive")
    @Operation(summary = "Get inactived establishments", description = "Retrieves an inactivad establishment")
    public ResponseEntity<Iterable<EstablishmentDto>> inactiveEstablishment(){
        Iterable<EstablishmentDto> establishment = establishmentService.findAllInactive();
        return ResponseEntity.status(200).body(establishment);
    }

    @Operation(summary = "List establishments by manager ID", description = "Retrieves a list of establishments associated with a manager ID")
    @GetMapping("/manager/{id}")
    public ResponseEntity<List<EstablishmentDto>> listByManagerId(@PathVariable Integer id) {
        List<EstablishmentDto> establishments = establishmentService.getByManagerId(id);

        if (establishments.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(establishments);
    }

    @GetMapping("/image/perfil/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<MediaEstablishmentDto> getPerfilImage(@PathVariable Integer id){
        return ResponseEntity.ok(establishmentService.getPerfilImage(id));
    }

    @GetMapping("/images/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<MediaEstablishmentDto>> getImages(@PathVariable Integer id){
        return ResponseEntity.ok(establishmentService.getImages(id));
    }
    
    @GetMapping("/inactivate/{id}")
    public ResponseEntity<EstablishmentDto> inactivateEverthing(@PathVariable Integer id){
        EstablishmentDto establishment = establishmentService.getById(id);
        deletionTree.createRoot(establishment, TypeForDeletionEnum.ESTABLISHMENT);
        deletionTree.insert(deletionTree.getRoot());
        deletionTree.deletionSequenceOnTree(deletionTree.getRoot());
        return ResponseEntity.status(200).body(establishment);
    }

    @PostMapping("/upload/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Integer id) throws IOException {
        String uploadFile = establishmentService.uploadFile(file, id);

        return ResponseEntity.ok(uploadFile);
    }

    @GetMapping(value = "/media/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getFiles(@PathVariable Integer imageId) {
        return ResponseEntity.ok(establishmentService.getMedia(imageId));
    }

    @DeleteMapping("/media/{imageId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<String> deleteFile(@PathVariable Integer imageId) {
        String deletedFile = establishmentService.deleteMedia(imageId);
        return ResponseEntity.ok(deletedFile);
    }
}
