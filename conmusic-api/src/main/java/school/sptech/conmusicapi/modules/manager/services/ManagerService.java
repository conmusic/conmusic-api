package school.sptech.conmusicapi.modules.manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.manager.dtos.CreateManagerDto;
import school.sptech.conmusicapi.modules.manager.dtos.ManagerDto;
import school.sptech.conmusicapi.modules.manager.dtos.UpdateManagerDto;
import school.sptech.conmusicapi.modules.manager.entities.Manager;
import school.sptech.conmusicapi.modules.manager.mapper.ManagerMapper;
import school.sptech.conmusicapi.modules.manager.repositories.IManagerRepository;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;
import school.sptech.conmusicapi.shared.exceptions.UserForbiddenActionException;

import java.util.Optional;

@Service
public class ManagerService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IManagerRepository managerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ManagerDto create(CreateManagerDto dto) {
        Boolean isEmailAlreadyInUse = userRepository.existsByEmail(dto.getEmail());

        if (isEmailAlreadyInUse) {
            throw new BusinessRuleException("Email is already in use.");
        }

        Boolean isCpfAlreadyInUse = userRepository.existsByCpf(dto.getCpf());

        if (isCpfAlreadyInUse) {
            throw new BusinessRuleException("CPF is already in use.");
        }

        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(hashedPassword);

        Manager createdManager = managerRepository.save(ManagerMapper.fromDto(dto));
        return ManagerMapper.toDto(createdManager);
    }

    public ManagerDto update(UpdateManagerDto dto, Integer id){
        Optional<Manager> managerOpt = managerRepository.findById(id);

        if (managerOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("Manager with id %d was not found.", id));
        }

        userRepository.findById(id).filter(manager ->
                manager.getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())
        ).orElseThrow(
                () -> new UserForbiddenActionException("User does not have permission to update this user.")
        );

        Boolean isEmailAlreadyInUse = userRepository.existsByEmail(dto.getEmail());

        if (isEmailAlreadyInUse && !managerOpt.get().getEmail().equals(dto.getEmail())) {
            throw new BusinessRuleException("Email is already in use.");
        }

        Boolean isCpfAlreadyInUse = userRepository.existsByCpf(dto.getCpf());

        if (isCpfAlreadyInUse && !managerOpt.get().getCpf().equals(dto.getCpf())) {
            throw new BusinessRuleException("CPF is already in use.");
        }

        Manager updatedArtist = ManagerMapper.fromDtoUpdate(dto, managerOpt.get());
        updatedArtist.setId(id);
        managerRepository.save(updatedArtist);
        return ManagerMapper.toDto(updatedArtist);
    }

    public ManagerDto getById(Integer id) {
        Optional<Manager> managerOpt = managerRepository.findById(id);

        if (managerOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("Manager with id %d was not found.", id));
        }

        return ManagerMapper.toDto(managerOpt.get());
    }
}
