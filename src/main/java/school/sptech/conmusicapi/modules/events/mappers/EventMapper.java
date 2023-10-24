package school.sptech.conmusicapi.modules.events.mappers;

import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.establishment.mappers.EstablishmentMapper;
import school.sptech.conmusicapi.modules.events.dtos.*;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.genre.mapper.GenreMapper;
import school.sptech.conmusicapi.modules.schedules.mappers.ScheduleMapper;
import school.sptech.conmusicapi.modules.show.entities.Show;

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
    public static Event fromUpdateDto(UpdateEventDto eventDto, Event e){
        e.setName(eventDto.getName());
        e.setDescription(eventDto.getDescription());
        e.setValue(eventDto.getValue());
        e.setCoverCharge(eventDto.getCoverCharge());
        e.setGenre(eventDto.getGenre());
        e.setSchedules(eventDto.getSchedules());
        return e;
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
    
    public static EventLineupExportDto toEventLineupExport(Show show) {
        EventLineupExportDto dto = new EventLineupExportDto();

        dto.setEventName(show.getEvent().getName());
        dto.setGenre(show.getEvent().getGenre().getName());
        dto.setEstablishmentName(show.getEvent().getEstablishment().getEstablishmentName());
        dto.setEstablishmentAddress(show.getEvent().getEstablishment().getAddress());
        dto.setEstablishmentCity(show.getEvent().getEstablishment().getCity());
        dto.setEstablishmentState(show.getEvent().getEstablishment().getState());
        dto.setStartDateTime(show.getSchedule().getStartDateTime());
        dto.setEndDateTime(show.getSchedule().getEndDateTime());
        dto.setArtistName(show.getArtist().getName());
        dto.setArtistInstagram(show.getArtist().getInstagram());

        return dto;
    }
}
