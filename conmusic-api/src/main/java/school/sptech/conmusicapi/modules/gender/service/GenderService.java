package school.sptech.conmusicapi.modules.gender.service;

import org.springframework.beans.factory.annotation.Autowired;
import school.sptech.conmusicapi.modules.gender.dto.DisplayingGenderDto;
import school.sptech.conmusicapi.modules.gender.dto.RegisterGenderDto;
import school.sptech.conmusicapi.modules.gender.entities.Gender;
import school.sptech.conmusicapi.modules.gender.mapper.GenderMapper;
import school.sptech.conmusicapi.modules.gender.repository.IGenderRepository;

public class GenderService {

    @Autowired
    IGenderRepository genderRepository;


    public DisplayingGenderDto register(RegisterGenderDto genderDto){

        Gender gender = GenderMapper.fromDto(genderDto);

        genderRepository.save(gender);

        return GenderMapper.toDto(gender);
    }
}
