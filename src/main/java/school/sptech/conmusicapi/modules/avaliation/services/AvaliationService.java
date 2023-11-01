package school.sptech.conmusicapi.modules.avaliation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.artist.entities.Artist;
import school.sptech.conmusicapi.modules.artist.repositories.IArtistRepository;
import school.sptech.conmusicapi.modules.avaliation.dtos.AvaliationDto;
import school.sptech.conmusicapi.modules.avaliation.dtos.CreateAvaliationDto;
import school.sptech.conmusicapi.modules.avaliation.entities.Avaliation;
import school.sptech.conmusicapi.modules.avaliation.mapper.AvaliationMapper;
import school.sptech.conmusicapi.modules.avaliation.repositories.IAvaliationRepository;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.establishment.repositories.IEstablishmentRepository;
import school.sptech.conmusicapi.modules.show.entities.Show;
import school.sptech.conmusicapi.modules.show.repositories.IShowRepository;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliationService {
    @Autowired
    private IArtistRepository artistRepository;
    @Autowired
    private IShowRepository showRepository;
    @Autowired
    private IAvaliationRepository avaliationRepository;

    public AvaliationDto create(CreateAvaliationDto dto){
        Artist artist = artistRepository.findById(dto.getArtistId()).orElseThrow(() -> new
                EntityNotFoundException(String.format("Artist with id %d was not found", dto.getArtistId())));
        Show show = showRepository.findById(dto.getShowId()).orElseThrow(() -> new
                EntityNotFoundException(String.format("Artist with id %d was not found", dto.getShowId()))) ;

        Avaliation avaliation= AvaliationMapper.fromDto(dto);
        avaliation.setArtist(artist);
        avaliation.setShow(show);
        Avaliation createdAvaliation = avaliationRepository.save(avaliation);
        return AvaliationMapper.toDto(createdAvaliation);
    }
    public List<AvaliationDto> listAll(){
        return avaliationRepository.findAll().stream().map(AvaliationMapper::toDto).toList();
    }

    public List<AvaliationDto> listByEstablshmentId(Integer id){
        return avaliationRepository.findByEstablishmentId(id).stream().map(AvaliationMapper::toDto).toList();
    }
}
