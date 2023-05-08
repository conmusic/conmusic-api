package school.sptech.conmusicapi.modules.gender.controller;

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
public class GenderController {

    @Autowired
    GenderService genderService;

    @PostMapping
    public ResponseEntity<DisplayingGenderDto> registerGender(@RequestBody @Valid RegisterGenderDto registerGenderDto){
        return ResponseEntity.status(201).body(genderService.register(registerGenderDto));
    }
}
