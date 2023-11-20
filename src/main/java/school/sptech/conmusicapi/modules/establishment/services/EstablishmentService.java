package school.sptech.conmusicapi.modules.establishment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.conmusicapi.modules.artist.entities.Artist;
import school.sptech.conmusicapi.modules.establishment.dtos.CreateEstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.dtos.EstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.dtos.UpdateEstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.establishment.mappers.EstablishmentMapper;
import school.sptech.conmusicapi.modules.establishment.repositories.IEstablishmentRepository;
import school.sptech.conmusicapi.modules.manager.entities.Manager;
import school.sptech.conmusicapi.modules.manager.repositories.IManagerRepository;
import school.sptech.conmusicapi.modules.media.dtos.MediaArtistDto;
import school.sptech.conmusicapi.modules.media.dtos.MediaEstablishmentDto;
import school.sptech.conmusicapi.modules.media.entities.Media;
import school.sptech.conmusicapi.modules.media.mapper.MediaMapper;
import school.sptech.conmusicapi.modules.media.repositories.IMediaRepository;
import school.sptech.conmusicapi.modules.media.services.StorageService;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstablishmentService {
    @Autowired
    private IEstablishmentRepository establishmentRepository;

    @Autowired
    private IManagerRepository managerRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private IMediaRepository mediaRepository;

    public EstablishmentDto create(CreateEstablishmentDto dto) {
        Optional<Manager> managerOpt = managerRepository.findById(dto.getManagerId());

        if (managerOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("Manager with id %d was not found.", dto.getManagerId()));
        }

        Boolean isCnpjAlreadyInUse = establishmentRepository.existsByCnpj(dto.getCnpj());

        if (isCnpjAlreadyInUse) {
            throw new BusinessRuleException("CNPJ is already in use.");
        }

        Establishment establishment = EstablishmentMapper.fromDto(dto);
        establishment.setManager(managerOpt.get());

        Establishment createdEstablishment = establishmentRepository.save(establishment);
        return EstablishmentMapper.toDto(createdEstablishment);
    }

    public EstablishmentDto update(UpdateEstablishmentDto dto, Integer id) {
        Optional<Establishment> establishmentOpt = establishmentRepository.findById(id);

        if (establishmentOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("Establishment with id %d was not found.", id));
        }

        Boolean isCnpjAlreadyInUse = establishmentRepository.existsByCnpj(dto.getCnpj());

        if (isCnpjAlreadyInUse) {
            throw new BusinessRuleException("CNPJ is already in use.");
        }

        Establishment updatedEstablishment = EstablishmentMapper.fromDtoUpdate(dto, establishmentOpt.get());
        updatedEstablishment.setId(id);
        establishmentRepository.save(updatedEstablishment);
        return EstablishmentMapper.toDto(updatedEstablishment);
    }

    public EstablishmentDto getById(Integer id) {
        Optional<Establishment> establishmentOpt = establishmentRepository.findById(id);

        if (establishmentOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("Establishment with id %d was not found.", id));
        }

        return EstablishmentMapper.toDto(establishmentOpt.get());
    }

    public List<EstablishmentDto> getByManagerId(Integer id) {
        Boolean managerExists = managerRepository.existsById(id);

        if (!managerExists) {
            throw new EntityNotFoundException(String.format("Manager with id %d does not exists.", id));
        }

        List<Establishment> establishments = establishmentRepository.findByManagerId(id);
        return establishments.stream().map(EstablishmentMapper::toDto).toList();
    }

    public String uploadFile(MultipartFile file, Integer id) throws IOException {

        Establishment establishment = establishmentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Establishment with id %d was not found.", id))
        );

        return storageService.uploadFileEstablishment(file, establishment);
    }

    public byte[] getMedia(Integer id){

        Media media = mediaRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Image with id %d not found.", id))
        );

        return storageService.downloadFile(media.getUrl());
    }

    public String deleteMedia(Integer imageId){

        Media media = mediaRepository.findById(imageId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Image with id %d not found.", imageId))
        );

        return storageService.deleteFile(media.getUrl());
    }

    public MediaEstablishmentDto getPerfilImage(Integer id) {

        Media media = mediaRepository.findByEstablishmentId(id).stream().findFirst().orElseThrow(
                () -> new EntityNotFoundException(String.format("Establishment with id %d was not found.", id))
        );

        return MediaMapper.mapToDtoEstablishment(media);
    }

    public List<MediaEstablishmentDto> getImages(Integer id) {

        List<Media> medias = mediaRepository.findByEstablishmentId(id);

        if (medias.isEmpty()){
            throw new EntityNotFoundException(String.format("Establishment with id %d was not found.", id));
        }

        return medias.stream()
                .map(MediaMapper::mapToDtoEstablishment)
                .toList();
    }
}
