package school.sptech.conmusicapi.modules.establishment.services;

import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
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
import school.sptech.conmusicapi.modules.media.entities.Media;
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
    private EntityManager entityManager;

    @Autowired
    private StorageService storageService;

    @Autowired
    private IMediaRepository mediaRepository;

    public void filterForInactive(boolean isDeleted){
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedEstablishmentFilter");
        Filter filterEvent = session.enableFilter("deletedEventsDtoFilter");
        filter.setParameter("isDeleted", isDeleted);
        filterEvent.setParameter("isDeleted", isDeleted);
        session.disableFilter("deletedProductFilter");
    }


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
        filterForInactive(false);
        Optional<Establishment> establishmentOpt = establishmentRepository.findById(id);
        if (establishmentOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("Establishment with id %d was not found.", id));
        }

        return EstablishmentMapper.toDto(establishmentOpt.get());
    }

    public List<EstablishmentDto> getByManagerId(Integer id) {
        filterForInactive(false);
        Boolean managerExists = managerRepository.existsById(id);

        if (!managerExists) {
            throw new EntityNotFoundException(String.format("Manager with id %d does not exists.", id));
        }

        List<Establishment> establishments = establishmentRepository.findByManagerId(id);
        return establishments.stream().map(EstablishmentMapper::toDto).toList();
    }

    public Iterable<EstablishmentDto> findAllInactive(){
        filterForInactive(true);
        List<EstablishmentDto> establishments =  establishmentRepository.findAll().stream().map(EstablishmentMapper :: toDto).toList();
        return (establishments);
    }

    public EstablishmentDto activateEstablishment(Integer id){
        Optional<Establishment> establishmentOpt = establishmentRepository.findById(id);

        if (establishmentOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("Establishment with id %d was not found.", id));
        }
        Establishment establishmentInactive = EstablishmentMapper.fromInactive(establishmentOpt.get(), false);
        establishmentRepository.save(establishmentInactive);

        return EstablishmentMapper.toDto(establishmentInactive);
    }

    public String uploadFile(MultipartFile file, Integer id) throws IOException {

        Establishment establishment = establishmentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Establishment with id %d was not found.", id))
        );

        return storageService.uploadFileEstablishment(file, establishment);
    }

    public List<ByteArrayResource> getMedia(Integer id){

        List<Media> medias = mediaRepository.findByEstablishmentId(id);

        List<ByteArrayResource> files = new ArrayList<>();

        medias.forEach(media -> {
            byte[] data = storageService.downloadFile(media.getUrl());
            ByteArrayResource resource = new ByteArrayResource(data);
            files.add(resource);
        });

        if (files.isEmpty()){
            throw new EntityNotFoundException(String.format("Establishment with id %d was not found.", id));
        }

        return files;
    }

    public String deleteMedia(String fileName){
        return storageService.deleteFile(fileName);
    }
}
