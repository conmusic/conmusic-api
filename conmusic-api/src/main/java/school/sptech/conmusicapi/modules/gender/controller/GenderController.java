package school.sptech.conmusicapi.modules.gender.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.conmusicapi.modules.gender.dto.DisplayingGenderDto;
import school.sptech.conmusicapi.modules.gender.dto.RegisterGenderDto;
import school.sptech.conmusicapi.modules.gender.entities.Gender;
import school.sptech.conmusicapi.modules.gender.service.GenderService;

@RestController
@RequestMapping("/generos")
@Tag(name = "Gender", description = "This endpoint is responsible for creating music genres in the API")
public class GenderController {

    @Autowired
    GenderService genderService;

    @PostMapping
    public ResponseEntity<DisplayingGenderDto> registerGender(@RequestBody @Valid RegisterGenderDto registerGenderDto){
        return ResponseEntity.status(201).body(genderService.register(registerGenderDto));
    }
}
