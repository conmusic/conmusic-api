package school.sptech.conmusicapi.modules.house.mapper;

import school.sptech.conmusicapi.modules.house.dtos.CreateHouseDto;
import school.sptech.conmusicapi.modules.house.dtos.HouseDto;
import school.sptech.conmusicapi.modules.house.entities.House;

public class HouseMapper {
    public static House fromDto(CreateHouseDto dto) {
        House house = new House();

        house.setName(dto.getName());
        house.setEmail(dto.getEmail());
        house.setPassword(dto.getPassword());
        house.setPhoneNumber(dto.getPhoneNumber());
        house.setAbout(dto.getAbout());
        house.setCnpj(dto.getCnpj());

        return house;
    }

    public static HouseDto toDto(House house) {
        HouseDto dto = new HouseDto();

        dto.setId(house.getId());
        dto.setName(house.getName());
        dto.setEmail(house.getEmail());
        dto.setPhoneNumber(house.getPhoneNumber());
        dto.setAbout(house.getAbout());
        dto.setCnpj(house.getCnpj());

        return dto;
    }
}
