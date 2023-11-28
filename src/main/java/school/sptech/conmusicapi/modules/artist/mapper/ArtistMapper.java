package school.sptech.conmusicapi.modules.artist.mapper;

import school.sptech.conmusicapi.modules.artist.dtos.*;
import school.sptech.conmusicapi.modules.artist.entities.Artist;
import school.sptech.conmusicapi.modules.user.mapper.UserMapper;

public class ArtistMapper {
    public static Artist fromDto(CreateArtistDto dto) {
        Artist artist = new Artist();

        UserMapper.fromDto(dto, artist);

        return artist;
    }

    public static Artist fromDtoUpdate(UpdateArtistDto dto, Artist artist){
        UserMapper.fromDtoUpdate(dto, artist);

        return artist;
    }

    public static ArtistDto toDto(Artist artist) {
        ArtistDto dto = new ArtistDto();

        UserMapper.toDto(artist, dto);
        dto.setMusicalGenres(artist.getMusicalGenres());

        return dto;
    }

    public static ShowArtistDto toShowArtistDto(Artist entity) {
        ShowArtistDto dto = new ShowArtistDto();

        UserMapper.toDto(entity, dto);
        dto.setMusicalGenres(entity.getMusicalGenres());

        return dto;
    }
}
