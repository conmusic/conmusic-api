package school.sptech.conmusicapi.modules.admin.mappers;

import school.sptech.conmusicapi.modules.admin.dtos.AdminDto;
import school.sptech.conmusicapi.modules.admin.dtos.CreateAdminDto;
import school.sptech.conmusicapi.modules.admin.entities.Admin;
import school.sptech.conmusicapi.modules.user.mapper.UserMapper;
import school.sptech.conmusicapi.modules.admin.dtos.AdminKpiDto;

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

    public static AdminKpiDto toKpiDto(String category, long total) {
        AdminKpiDto kpi = new AdminKpiDto();

        kpi.setCategory(category);
        kpi.setTotal(total);

        return kpi;
    }

    public static AdminKpiDto toKpiDto(String category, long byArtist, long byManager) {
        AdminKpiDto kpi = new AdminKpiDto();

        kpi.setCategory(category);
        kpi.setByArtist(byArtist);
        kpi.setByManager(byManager);
        kpi.setTotal(byArtist + byManager);

        return kpi;
    }
}
