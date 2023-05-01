package school.sptech.conmusicapi.modules.establishment.mappers;

import school.sptech.conmusicapi.modules.establishment.dtos.CreateEstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.dtos.EstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;

public class EstablishmentMapper {
    public static Establishment fromDto(CreateEstablishmentDto dto) {
        Establishment establishment = new Establishment();

        establishment.setCnpj(dto.getCnpj());
        establishment.setFantasyName(dto.getFantasyName());
        establishment.setEstablishmentName(dto.getEstablishmentName());
        establishment.setPhoneNumber(dto.getPhoneNumber());
        establishment.setAmount110Outlets(dto.getAmount110Outlets());
        establishment.setAmount220Outlets(dto.getAmount220Outlets());
        establishment.setCapacity(dto.getCapacity());
        establishment.setAddress(dto.getAddress());
        establishment.setCity(dto.getCity());
        establishment.setState(dto.getState());
        establishment.setZipCode(dto.getZipCode());

        return establishment;
    }

    public static EstablishmentDto toDto(Establishment establishment) {
        EstablishmentDto dto = new EstablishmentDto();

        dto.setId(establishment.getId());
        dto.setCnpj(establishment.getCnpj());
        dto.setFantasyName(establishment.getFantasyName());
        dto.setEstablishmentName(establishment.getEstablishmentName());
        dto.setPhoneNumber(establishment.getPhoneNumber());
        dto.setAmount110Outlets(establishment.getAmount110Outlets());
        dto.setAmount220Outlets(establishment.getAmount220Outlets());
        dto.setCapacity(establishment.getCapacity());
        dto.setAddress(establishment.getAddress());
        dto.setCity(establishment.getCity());
        dto.setState(establishment.getState());
        dto.setZipCode(establishment.getZipCode());
        dto.setManagerId(establishment.getManager().getId());

        return dto;
    }
}
