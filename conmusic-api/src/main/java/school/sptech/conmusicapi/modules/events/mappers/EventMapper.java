package school.sptech.conmusicapi.modules.events.mappers;

import school.sptech.conmusicapi.modules.establishment.mappers.EstablishmentMapper;
import school.sptech.conmusicapi.modules.events.dtos.CreateEventDto;
import school.sptech.conmusicapi.modules.events.dtos.EventDto;
import school.sptech.conmusicapi.modules.events.entities.Event;

public class EventMapper {
    public static Event fromDto(CreateEventDto dto) {
        Event entity = new Event();

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setValue(dto.getValue());
        entity.setCoverCharge(dto.getCoverCharge());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setUnique(dto.getUnique());

        return entity;
    }

    public static EventDto toDto(Event entity) {
        EventDto dto = new EventDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setValue(entity.getValue());
        dto.setCoverCharge(entity.getCoverCharge());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setUnique(entity.getUnique());
        dto.setEstablishment(EstablishmentMapper.toDto(entity.getEstablishment()));

        return dto;
    }
}
