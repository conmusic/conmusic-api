package school.sptech.conmusicapi.modules.artist.mapper;

import school.sptech.conmusicapi.modules.artist.dtos.ArtistDto;
import school.sptech.conmusicapi.modules.artist.dtos.CreateArtistDto;
import school.sptech.conmusicapi.modules.artist.dtos.UpdateArtistDto;
import school.sptech.conmusicapi.modules.artist.entities.Artist;
import school.sptech.conmusicapi.modules.user.entities.User;
import school.sptech.conmusicapi.modules.user.mapper.UserMapper;

public class ArtistMapper {
    public static Artist fromDto(CreateArtistDto dto) {
        Artist artist = new Artist();

        UserMapper.fromDto(dto, artist);
        artist.setCpf(dto.getCpf());

        return artist;
    }

    public static Artist fromDtoUpdate(UpdateArtistDto dto, Artist artist){
        UserMapper.fromDtoUpdate(dto, artist);
        artist.setCpf(dto.getCpf());

        return artist;
    }

    public static ArtistDto toDto(Artist artist) {
        ArtistDto dto = new ArtistDto();

        UserMapper.toDto(artist, dto);
        dto.setCpf(artist.getCpf());

        return dto;
    }
}
