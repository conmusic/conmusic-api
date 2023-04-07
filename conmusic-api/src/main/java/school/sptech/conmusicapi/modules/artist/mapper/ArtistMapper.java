package school.sptech.conmusicapi.modules.artist.mapper;

import school.sptech.conmusicapi.modules.artist.dtos.ArtistDto;
import school.sptech.conmusicapi.modules.artist.dtos.CreateArtistDto;
import school.sptech.conmusicapi.modules.artist.entities.Artist;

public class ArtistMapper {
    public static Artist fromDto(CreateArtistDto dto) {
        Artist artist = new Artist();

        artist.setName(dto.getName());
        artist.setEmail(dto.getEmail());
        artist.setPassword(dto.getPassword());
        artist.setPhoneNumber(dto.getPhoneNumber());
        artist.setAbout(dto.getAbout());
        artist.setCpf(dto.getCpf());

        return artist;
    }

    public static ArtistDto toDto(Artist artist) {
        ArtistDto dto = new ArtistDto();

        dto.setId(artist.getId());
        dto.setName(artist.getName());
        dto.setEmail(artist.getEmail());
        dto.setPhoneNumber(artist.getPhoneNumber());
        dto.setAbout(artist.getAbout());
        dto.setCpf(artist.getCpf());

        return dto;
    }
}
