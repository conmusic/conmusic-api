package school.sptech.conmusicapi.modules.gender.mapper;

import school.sptech.conmusicapi.modules.gender.dto.DisplayingGenderDto;
import school.sptech.conmusicapi.modules.gender.dto.RegisterGenderDto;
import school.sptech.conmusicapi.modules.gender.entities.Gender;

public class GenderMapper {

    public static Gender fromDto(RegisterGenderDto registerGenderDto){

        Gender gender = new Gender();

        gender.setName(registerGenderDto.getName());

        return gender;
    }

    public static DisplayingGenderDto toDto(Gender gender){
        DisplayingGenderDto genderDto = new DisplayingGenderDto();

        genderDto.setName(gender.getName());

        return genderDto;
    }
}
