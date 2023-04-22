package school.sptech.conmusicapi.modules.house.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.conmusicapi.modules.house.dtos.CreateHouseDto;
import school.sptech.conmusicapi.modules.house.dtos.HouseDto;
import school.sptech.conmusicapi.modules.house.dtos.UpdateHouseDto;
import school.sptech.conmusicapi.modules.house.services.HouseService;

import java.util.Optional;

@RestController
@RequestMapping("/houses")
@Tag(name = "Houses", description = "Responsible for managing all requests and operations related to house users")
public class HouseController {
    @Autowired
    private HouseService houseService;

    @PostMapping
    public ResponseEntity<HouseDto> create(@RequestBody @Valid CreateHouseDto dto) {
        Optional<HouseDto> createdHouse = houseService.create(dto);

        if (createdHouse.isEmpty()) {
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.status(201).body(createdHouse.get());
    }

    @PutMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<HouseDto> update(@RequestBody @Valid UpdateHouseDto dto){
        Optional<HouseDto> updatedHouse = houseService.update(dto);

        if (updatedHouse.isEmpty()){
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.status(200).body(updatedHouse.get());
    }
}
