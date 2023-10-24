package school.sptech.conmusicapi.modules.schedules.mappers;

import school.sptech.conmusicapi.modules.establishment.dtos.InactiveEstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.events.mappers.EventMapper;
import school.sptech.conmusicapi.modules.schedules.dtos.BasicScheduleDto;
import school.sptech.conmusicapi.modules.schedules.dtos.CreateScheduleDto;
import school.sptech.conmusicapi.modules.schedules.dtos.InactivateScheduleDto;
import school.sptech.conmusicapi.modules.schedules.dtos.ReadScheduleDto;
import school.sptech.conmusicapi.modules.schedules.dtos.ScheduleDto;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;

import java.time.LocalDateTime;

public class ScheduleMapper {

    public static Schedule fromDto(CreateScheduleDto dto) {
        Schedule entity = new Schedule();

        entity.setStartDateTime(dto.getStartDateTime());
        entity.setEndDateTime(dto.getEndDateTime());
        entity.setConfirmed(false);

        return entity;
    }
    public static Schedule fromInactive(Schedule e, Boolean deleted){
        e.setDeleted(deleted);
        return e;
    }
    public static ScheduleDto toDto(Schedule entity) {
        ScheduleDto dto = new ScheduleDto();

        dto.setId(entity.getId());
        dto.setStartDateTime(entity.getStartDateTime());
        dto.setEndDateTime(entity.getEndDateTime());
        dto.setConfirmed(entity.getConfirmed());
        dto.setEvent(EventMapper.toDisplayScheduleDto(entity.getEvent()));

        return dto;
    }

    public static BasicScheduleDto toBasicDto(Schedule entity) {
        BasicScheduleDto dto = new BasicScheduleDto();

        dto.setId(entity.getId());
        dto.setStartDateTime(entity.getStartDateTime());
        dto.setEndDateTime(entity.getEndDateTime());
        dto.setConfirmed(entity.getConfirmed());

        return dto;
    }

    public static ReadScheduleDto toReadScheduleDto(Integer eventId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        ReadScheduleDto imported = new ReadScheduleDto();

        imported.setEventId(eventId);
        imported.setStartDateTime(startDateTime.withSecond(0));
        imported.setEndDateTime(endDateTime.withSecond(0));

        return imported;
    }

    public static CreateScheduleDto toCreateDto(ReadScheduleDto imported) {
        CreateScheduleDto dto = new CreateScheduleDto();

        dto.setStartDateTime(imported.getStartDateTime());
        dto.setEndDateTime(imported.getEndDateTime());

        return dto;
    }
}
