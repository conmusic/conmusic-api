package school.sptech.conmusicapi.modules.schedules.mappers;

import school.sptech.conmusicapi.modules.schedules.dtos.BasicScheduleDto;
import school.sptech.conmusicapi.modules.schedules.dtos.CreateScheduleDto;
import school.sptech.conmusicapi.modules.schedules.dtos.ScheduleDto;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;
import school.sptech.conmusicapi.shared.mappers.TimeMapper;

public class ScheduleMapper {
    public static Schedule fromDto(CreateScheduleDto dto) {
        Schedule schedule = new Schedule();

        schedule.setDayOfWeek(dto.getDayOfWeek());
        schedule.setStartTime(dto.getStartTime().asLocalTime());
        schedule.setEndTime(dto.getEndTime().asLocalTime());

        return schedule;
    }

    public static ScheduleDto toDto(Schedule entity) {
        ScheduleDto dto = new ScheduleDto();

        dto.setId(entity.getId());
        dto.setDayOfWeek(entity.getDayOfWeek());
        dto.setStartTime(TimeMapper.toDto(entity.getStartTime()));
        dto.setEndTime(TimeMapper.toDto(entity.getEndTime()));
        dto.setEventId(entity.getId());

        return dto;
    }

    public static BasicScheduleDto toBasicDto(Schedule entity) {
        BasicScheduleDto dto = new BasicScheduleDto();

        dto.setId(entity.getId());
        dto.setDayOfWeek(entity.getDayOfWeek());
        dto.setStartTime(TimeMapper.toDto(entity.getStartTime()));
        dto.setEndTime(TimeMapper.toDto(entity.getEndTime()));

        return dto;
    }
}
