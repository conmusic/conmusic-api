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
import school.sptech.conmusicapi.modules.avaliation.util.RoundAvaliation;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.establishment.repositories.IEstablishmentRepository;
import school.sptech.conmusicapi.modules.show.entities.Show;
import school.sptech.conmusicapi.modules.show.repositories.IShowRepository;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
public class AvaliationService {
    @Autowired
    private IArtistRepository artistRepository;
    @Autowired
    private IEstablishmentRepository establishmentRepository;
    @Autowired
    private IAvaliationRepository avaliationRepository;

    public AvaliationDto create(CreateAvaliationDto dto) {
        Artist artist = artistRepository.findById(dto.getArtistId()).orElseThrow(() -> new
                EntityNotFoundException(String.format("Artist with id %d was not found", dto.getArtistId())));
        Establishment establishment = establishmentRepository.findById(dto.getEstablishmentId()).orElseThrow(() -> new
                EntityNotFoundException(String.format("Establishment with id %d was not found", dto.getEstablishmentId())));

        Avaliation avaliation = AvaliationMapper.fromDto(dto);
        avaliation.setArtist(artist);
        avaliation.setEstablishment(establishment);
        Avaliation createdAvaliation = avaliationRepository.save(avaliation);
        return AvaliationMapper.toDto(createdAvaliation);
    }

    public Double establishmentMedia(Integer id) {
        List<Avaliation> avaliation= avaliationRepository.findByEstablishmentId(id);
        Double rating =0.0;
        for (int i = 0; i < avaliation.size(); i++) {
            rating += avaliation.get(i).getRating();
        }
        Double avarege = rating/avaliation.size();
        return RoundAvaliation.round(avarege);
    }

    public List<AvaliationDto> listAll() {
        return avaliationRepository.findAll().stream().map(AvaliationMapper::toDto).toList();
    }

    public List<AvaliationDto> listByEstablishmentId(Integer id) {
        return avaliationRepository.findByEstablishmentId(id).stream().map(AvaliationMapper::toDto).toList();
    }

    public List<AvaliationDto> listByArtistId(Integer id) {
        return avaliationRepository.findByArtistId(id).stream().map(AvaliationMapper::toDto).toList();
    }
}
