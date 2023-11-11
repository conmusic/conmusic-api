package school.sptech.conmusicapi.modules.manager.mapper;

import school.sptech.conmusicapi.modules.manager.dtos.CreateManagerDto;
import school.sptech.conmusicapi.modules.manager.dtos.ManagerDto;
import school.sptech.conmusicapi.modules.manager.dtos.UpdateManagerDto;
import school.sptech.conmusicapi.modules.manager.entities.Manager;
import school.sptech.conmusicapi.modules.user.mapper.UserMapper;

public class ManagerMapper {
    public static Manager fromDto(CreateManagerDto dto) {
        Manager manager = new Manager();

        UserMapper.fromDto(dto, manager);

        return manager;
    }

    public static Manager fromDtoUpdate(UpdateManagerDto dto, Manager manager){
        UserMapper.fromDtoUpdate(dto, manager);

        return manager;
    }

    public static ManagerDto toDto(Manager manager) {
        ManagerDto dto = new ManagerDto();

        UserMapper.toDto(manager, dto);

        return dto;
    }
}
