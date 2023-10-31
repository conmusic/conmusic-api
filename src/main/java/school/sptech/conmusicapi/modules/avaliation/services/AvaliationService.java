package school.sptech.conmusicapi.modules.avaliation.services;

import org.springframework.beans.factory.annotation.Autowired;
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

public class AvaliationService {
    @Autowired
    private IEstablishmentRepository establishmentRepository;
    @Autowired
    private IAvaliationRepository avaliationRepository;

    public AvaliationDto create(CreateAvaliationDto dto){
        Optional<Establishment> establishment = establishmentRepository.findById(dto.getEstablishmentId());

        if (establishment.isEmpty()){
            throw new EntityNotFoundException(String.format("Establishment with id %d was not found", dto.getEstablishmentId()));
        }

        Avaliation avaliation= AvaliationMapper.fromDto(dto);
        avaliation.setEstablishment(establishment.get());
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
