package school.sptech.conmusicapi.modules.establishment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.establishment.dtos.CreateEstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.dtos.EstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.establishment.mappers.EstablishmentMapper;
import school.sptech.conmusicapi.modules.establishment.repositories.IEstablishmentRepository;
import school.sptech.conmusicapi.modules.manager.entities.Manager;
import school.sptech.conmusicapi.modules.manager.repositories.IManagerRepository;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;

import java.util.Optional;

@Service
public class EstablishmentService {
    @Autowired
    private IEstablishmentRepository establishmentRepository;

    @Autowired
    private IManagerRepository managerRepository;

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
}
