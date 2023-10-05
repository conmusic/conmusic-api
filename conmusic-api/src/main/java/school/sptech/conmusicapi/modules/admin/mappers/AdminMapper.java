package school.sptech.conmusicapi.modules.admin.mappers;

import school.sptech.conmusicapi.modules.admin.dtos.AdminDto;
import school.sptech.conmusicapi.modules.admin.dtos.CreateAdminDto;
import school.sptech.conmusicapi.modules.admin.entities.Admin;
import school.sptech.conmusicapi.modules.user.mapper.UserMapper;

public class AdminMapper {
    public static AdminDto toDto(Admin entity) {
        AdminDto adminDto = new AdminDto();

        UserMapper.toDto(entity, adminDto);

        return adminDto;
    }

    public static Admin fromDto(CreateAdminDto dto) {
        Admin admin = new Admin();

        UserMapper.fromDto(dto, admin);

        return admin;
    }
}