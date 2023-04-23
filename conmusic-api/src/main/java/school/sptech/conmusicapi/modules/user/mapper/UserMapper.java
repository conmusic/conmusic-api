package school.sptech.conmusicapi.modules.user.mapper;

import school.sptech.conmusicapi.modules.artist.dtos.UpdateArtistDto;
import school.sptech.conmusicapi.modules.user.dtos.CreateUserDto;
import school.sptech.conmusicapi.modules.user.dtos.UpdateUserDto;
import school.sptech.conmusicapi.modules.user.dtos.UserDto;
import school.sptech.conmusicapi.modules.user.dtos.UserTokenDto;
import school.sptech.conmusicapi.modules.user.entities.User;

public class UserMapper {
    public static void fromDto(CreateUserDto dto, User user) {
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setCpf(dto.getCpf());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setBirthDate(dto.getBirthDate());
        user.setAbout(dto.getAbout());
        user.setInstagram(dto.getInstagram());
    }

    public static void fromDtoUpdate(UpdateUserDto dto, User user){
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setCpf(dto.getCpf());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAbout(dto.getAbout());
        user.setInstagram(dto.getInstagram());
    }

    public static void toDto(User user, UserDto dto) {
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCpf(user.getCpf());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setBirthDate(user.getBirthDate());
        dto.setAbout(user.getAbout());
        dto.setInstagram(user.getInstagram());
    }
}
