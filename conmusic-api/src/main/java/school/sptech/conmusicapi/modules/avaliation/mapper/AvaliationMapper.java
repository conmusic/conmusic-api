package school.sptech.conmusicapi.modules.avaliation.mapper;

import school.sptech.conmusicapi.modules.avaliation.dtos.CreateAvaliationDto;
import school.sptech.conmusicapi.modules.avaliation.entities.Avaliation;
import school.sptech.conmusicapi.modules.avaliation.repositories.IAvaliationRepository;
import school.sptech.conmusicapi.modules.show.repositories.IShowRepository;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;

public class AvaliationMapper {
    private IShowRepository showRepository;
    private IUserRepository userRepository;
    private IAvaliationRepository avaliationRepository;
    public Avaliation fromDto(CreateAvaliationDto dto){
        Avaliation entity = new Avaliation();

        entity.setRating(dto.getRating());
        entity.setComentary(dto.getComentary());

        return entity;
    }
    public CreateAvaliationDto toDto(Avaliation entity){
        return null;
    }

}
