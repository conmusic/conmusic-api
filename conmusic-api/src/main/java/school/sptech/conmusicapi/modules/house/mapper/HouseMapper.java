package school.sptech.conmusicapi.modules.house.mapper;

import school.sptech.conmusicapi.modules.house.dtos.CreateHouseDto;
import school.sptech.conmusicapi.modules.house.dtos.HouseDto;
import school.sptech.conmusicapi.modules.house.entities.House;
import school.sptech.conmusicapi.modules.user.mapper.UserMapper;

public class HouseMapper {
    public static House fromDto(CreateHouseDto dto) {
        House house = new House();

        UserMapper.fromDto(dto, house);
        house.setCnpj(dto.getCnpj());

        return house;
    }

    public static HouseDto toDto(House house) {
        HouseDto dto = new HouseDto();

        UserMapper.toDto(house, dto);
        dto.setCnpj(house.getCnpj());

        return dto;
    }
}
