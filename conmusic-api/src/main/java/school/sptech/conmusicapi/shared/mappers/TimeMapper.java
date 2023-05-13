package school.sptech.conmusicapi.shared.mappers;

import school.sptech.conmusicapi.shared.dtos.TimeDto;

import java.time.LocalTime;

public class TimeMapper {
    public static TimeDto toDto(LocalTime localTime) {
        TimeDto dto = new TimeDto();

        dto.setHour(localTime.getHour());
        dto.setMinute(localTime.getMinute());

        return dto;
    }
}
