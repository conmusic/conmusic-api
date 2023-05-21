package school.sptech.conmusicapi.modules.schedules.mappers;

import school.sptech.conmusicapi.modules.events.mappers.EventMapper;
import school.sptech.conmusicapi.modules.schedules.dtos.BasicScheduleDto;
import school.sptech.conmusicapi.modules.schedules.dtos.CreateScheduleDto;
import school.sptech.conmusicapi.modules.schedules.dtos.ScheduleDto;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;
import school.sptech.conmusicapi.shared.mappers.TimeMapper;

public class ScheduleMapper {
    public static Schedule fromDto(CreateScheduleDto dto) {
        Schedule entity = new Schedule();

        entity.setStartDateTime(dto.getStartDateTime());
        entity.setEndDateTime(dto.getEndDateTime());
        entity.setConfirmed(false);

        return entity;
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
}
