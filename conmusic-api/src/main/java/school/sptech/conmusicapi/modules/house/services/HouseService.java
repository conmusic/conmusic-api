package school.sptech.conmusicapi.modules.house.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.house.dtos.CreateHouseDto;
import school.sptech.conmusicapi.modules.house.dtos.HouseDto;
import school.sptech.conmusicapi.modules.house.entities.House;
import school.sptech.conmusicapi.modules.house.mapper.HouseMapper;
import school.sptech.conmusicapi.modules.house.repositories.IHouseRepository;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;

import java.util.Optional;

@Service
public class HouseService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IHouseRepository houseRepository;

    public Optional<HouseDto> create(CreateHouseDto dto) {
        Boolean isEmailAlreadyInUse = userRepository.existsByEmail(dto.getEmail());

        if (isEmailAlreadyInUse) {
            return Optional.empty();
        }

        Boolean isCpfAlreadyInUse = houseRepository.existsByCnpj(dto.getCnpj());

        if (isCpfAlreadyInUse) {
            return Optional.empty();
        }

        House createdHouse = houseRepository.save(HouseMapper.fromDto(dto));
        return Optional.of(HouseMapper.toDto(createdHouse));
    }
}
