package school.sptech.conmusicapi.modules.event.mapper;

import school.sptech.conmusicapi.modules.event.dtos.ScheduleDto;
import school.sptech.conmusicapi.modules.event.entities.Schedule;

public class ScheduleMapper {
    public static ScheduleDto toDto(Schedule schedule) {
        ScheduleDto dto = new ScheduleDto();

        dto.setDayWeek(schedule.getDayWeek());
        dto.setId(schedule.getId());
        dto.setEndTime(schedule.getEndTime());
        dto.setStartTime(schedule.getStartTime());
        dto.setEventId(schedule.getEvent().getId());

        return dto;
    }
}
