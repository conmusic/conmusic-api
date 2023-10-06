package school.sptech.conmusicapi.modules.events.mappers;

import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.establishment.mappers.EstablishmentMapper;
import school.sptech.conmusicapi.modules.events.dtos.CreateEventDto;
import school.sptech.conmusicapi.modules.events.dtos.DisplayEstablishmentEventDto;
import school.sptech.conmusicapi.modules.events.dtos.DisplayScheduleEventDto;
import school.sptech.conmusicapi.modules.events.dtos.EventDto;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.genre.mapper.GenreMapper;
import school.sptech.conmusicapi.modules.schedules.mappers.ScheduleMapper;

public class EventMapper {
    public static Event fromDto(CreateEventDto dto) {
        Event entity = new Event();

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setValue(dto.getValue());
        entity.setCoverCharge(dto.getCoverCharge());

        return entity;
    }

    public static EventDto toDto(Event entity) {
        EventDto dto = new EventDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setValue(entity.getValue());
        dto.setCoverCharge(entity.getCoverCharge());
        dto.setEstablishment(EstablishmentMapper.toDisplayScheduleEstablishmentDto(entity.getEstablishment()));
        dto.setGenre(GenreMapper.toDto(entity.getGenre()));
        dto.setSchedules(entity.getSchedules().stream().map(ScheduleMapper::toBasicDto).toList());

        return dto;
    }

    public static DisplayScheduleEventDto toDisplayScheduleDto(Event entity) {
        DisplayScheduleEventDto dto = new DisplayScheduleEventDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setValue(entity.getValue());
        dto.setCoverCharge(entity.getCoverCharge());
        dto.setGenre(GenreMapper.toDto(entity.getGenre()));
        dto.setEstablishment(EstablishmentMapper.toDisplayScheduleEstablishmentDto(entity.getEstablishment()));

        return dto;
    }

    public static DisplayEstablishmentEventDto toDisplayEstablishmentEventDto(Event entity) {
        DisplayEstablishmentEventDto dto = new DisplayEstablishmentEventDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setValue(entity.getValue());
        dto.setCoverCharge(entity.getCoverCharge());
        dto.setGenre(GenreMapper.toDto(entity.getGenre()));

        return dto;
    }

    public static Event fromInactive(Event e, Boolean deleted){
        e. setDeleted(deleted);
        return e;
    }
}
