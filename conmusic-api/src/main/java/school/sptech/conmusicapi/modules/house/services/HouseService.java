package school.sptech.conmusicapi.modules.house.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.house.dtos.CreateHouseDto;
import school.sptech.conmusicapi.modules.house.dtos.HouseDto;
import school.sptech.conmusicapi.modules.house.dtos.UpdateHouseDto;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<HouseDto> create(CreateHouseDto dto) {
        Boolean isEmailAlreadyInUse = userRepository.existsByEmail(dto.getEmail());

        if (isEmailAlreadyInUse) {
            return Optional.empty();
        }

        Boolean isCpfAlreadyInUse = houseRepository.existsByCnpj(dto.getCnpj());

        if (isCpfAlreadyInUse) {
            return Optional.empty();
        }

        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(hashedPassword);

        House createdHouse = houseRepository.save(HouseMapper.fromDto(dto));
        return Optional.of(HouseMapper.toDto(createdHouse));
    }

    public Optional<HouseDto> update(UpdateHouseDto dto){

        if (userRepository.existsById(dto.getId())){
            House house = houseRepository.findById(dto.getId()).get();
            House updatedHouse = HouseMapper.fromDtoUpdate(dto, house);

            houseRepository.save(updatedHouse);
            return Optional.of(HouseMapper.toDto(updatedHouse));
        }

        return Optional.empty();

    }
}
