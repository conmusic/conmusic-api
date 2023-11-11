package school.sptech.conmusicapi.modules.admin.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import school.sptech.conmusicapi.modules.admin.dtos.AdminDto;
import school.sptech.conmusicapi.modules.admin.dtos.CreateAdminDto;
import school.sptech.conmusicapi.modules.admin.services.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admins")
@Tag(name = "Admins", description = "Responsible for managing all requests and operations related to admin users")
@SecurityRequirement(name = "Bearer")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Operation(summary = "Create admin", description = "Creates a new admin")
    @PostMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<AdminDto> create(@RequestBody @Valid CreateAdminDto dto) {
        AdminDto adminDto = adminService.create(dto);
        return ResponseEntity.status(201).body(adminDto);
    }


    @Operation(summary = "Get admin by ID", description = "Get admin details by ID")
    @GetMapping("/{adminId}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable Integer adminId) {
        AdminDto adminDto = adminService.getAdminById(adminId);
        return ResponseEntity.ok(adminDto);
    }

    @Operation(summary = "Get all admins", description = "Get a list of all admins")
    @GetMapping
    public ResponseEntity<List<AdminDto>> getAllAdmins() {
        List<AdminDto> adminDtos = adminService.getAllAdmins();
        return ResponseEntity.ok(adminDtos);
    }

    @Operation(summary = "Update an admin by ID", description = "Update admin details by ID")
    @PutMapping("/{adminId}")
    public ResponseEntity<AdminDto> updateAdmin(
            @PathVariable Integer adminId,
            @RequestBody AdminDto adminDto
    ) {
        AdminDto updatedAdmin = adminService.updateAdmin(adminId, adminDto);
        return ResponseEntity.ok(updatedAdmin);
    }
}
