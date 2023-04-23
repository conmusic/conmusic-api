package school.sptech.conmusicapi.modules.manager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.manager.dtos.CreateManagerDto;
import school.sptech.conmusicapi.modules.manager.dtos.ManagerDto;
import school.sptech.conmusicapi.modules.manager.dtos.UpdateManagerDto;
import school.sptech.conmusicapi.modules.manager.entities.Manager;
import school.sptech.conmusicapi.modules.manager.mapper.ManagerMapper;
import school.sptech.conmusicapi.modules.manager.repositories.IManagerRepository;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;

import java.util.Optional;

@Service
public class ManagerService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IManagerRepository managerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<ManagerDto> create(CreateManagerDto dto) {
        Boolean isEmailAlreadyInUse = userRepository.existsByEmail(dto.getEmail());

        if (isEmailAlreadyInUse) {
            return Optional.empty();
        }

        Boolean isCpfAlreadyInUse = userRepository.existsByCpf(dto.getCpf());

        if (isCpfAlreadyInUse) {
            return Optional.empty();
        }

        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(hashedPassword);

        Manager createdManager = managerRepository.save(ManagerMapper.fromDto(dto));
        return Optional.of(ManagerMapper.toDto(createdManager));
    }

    public Optional<ManagerDto> update(UpdateManagerDto dto){

        if (userRepository.existsById(dto.getId())){
            Manager manager = managerRepository.findById(dto.getId()).get();
            Manager updatedManager = ManagerMapper.fromDtoUpdate(dto, manager);

            managerRepository.save(updatedManager);
            return Optional.of(ManagerMapper.toDto(updatedManager));
        }

        return Optional.empty();

    }
}
