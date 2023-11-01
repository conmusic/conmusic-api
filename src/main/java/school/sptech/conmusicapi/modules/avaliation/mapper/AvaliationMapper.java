package school.sptech.conmusicapi.modules.avaliation.mapper;

import school.sptech.conmusicapi.modules.artist.mapper.ArtistMapper;
import school.sptech.conmusicapi.modules.avaliation.dtos.AvaliationDto;
import school.sptech.conmusicapi.modules.avaliation.dtos.CreateAvaliationDto;
import school.sptech.conmusicapi.modules.avaliation.entities.Avaliation;
import school.sptech.conmusicapi.modules.avaliation.repositories.IAvaliationRepository;
import school.sptech.conmusicapi.modules.establishment.mappers.EstablishmentMapper;
import school.sptech.conmusicapi.modules.show.mapper.ShowMapper;
import school.sptech.conmusicapi.modules.show.repositories.IShowRepository;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;

public class AvaliationMapper {

    public static Avaliation fromDto(CreateAvaliationDto dto){
        Avaliation entity = new Avaliation();

        entity.setRating(dto.getRating());
        entity.setComentary(dto.getComentary());

        return entity;
    }
    public static AvaliationDto toDto(Avaliation entity){
        AvaliationDto dto = new AvaliationDto();
        dto.setId(entity.getId());
        dto.setComentary(entity.getComentary());
        dto.setRating(entity.getRating());
        dto.setArtistDto(ArtistMapper.toDto(entity.getArtist()));
        dto.setShowDto(ShowMapper.toDto(entity.getShow()));

        return dto;
    }

}
