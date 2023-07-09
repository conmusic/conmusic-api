package school.sptech.conmusicapi.modules.admin.controllers;

import io.swagger.v3.oas.annotations.Operation;
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
import school.sptech.conmusicapi.modules.admin.dtos.AdminDto;
import school.sptech.conmusicapi.modules.admin.dtos.CreateAdminDto;
import school.sptech.conmusicapi.modules.admin.services.AdminService;

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
}
