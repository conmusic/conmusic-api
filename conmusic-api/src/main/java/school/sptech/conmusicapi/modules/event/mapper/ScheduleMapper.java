package school.sptech.conmusicapi.modules.event.mapper;

import school.sptech.conmusicapi.modules.event.dtos.ScheduleDto;
import school.sptech.conmusicapi.modules.event.entities.Schedule;
import school.sptech.conmusicapi.shared.dtos.TimeDto;

import java.time.LocalTime;

public class ScheduleMapper {
    public static ScheduleDto toDto(Schedule schedule) {
        ScheduleDto dto = new ScheduleDto();

        dto.setDayWeek(schedule.getDayWeek());
        dto.setId(schedule.getId());
        dto.setEndTime(ScheduleMapper.toDto(schedule.getEndTime()));
        dto.setStartTime(ScheduleMapper.toDto(schedule.getStartTime()));
        dto.setEventId(schedule.getEvent().getId());

        return dto;
    }

    public static TimeDto toDto(LocalTime localTime) {
        TimeDto dto = new TimeDto();

        dto.setHour(localTime.getHour());
        dto.setMinute(localTime.getMinute());

        return dto;
    }
}
