package school.sptech.conmusicapi.modules.user.mapper;

import school.sptech.conmusicapi.modules.user.dtos.UserTokenDto;
import school.sptech.conmusicapi.modules.user.entities.User;

public class UserTokenMapper {
    public static UserTokenDto toDto(User user, String token) {
        UserTokenDto dto = new UserTokenDto();

        dto.setId(user.getId());
        dto.setUser(UserMapper.toLoginDto(user));
        dto.setToken(token);

        return dto;
    }
}
