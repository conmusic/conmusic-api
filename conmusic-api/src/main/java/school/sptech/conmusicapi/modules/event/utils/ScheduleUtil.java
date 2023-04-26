package school.sptech.conmusicapi.modules.event.utils;

import school.sptech.conmusicapi.modules.event.dtos.CreateScheduleDto;
import school.sptech.conmusicapi.shared.dtos.TimeDto;

import java.time.*;

public class ScheduleUtil {
    private static Boolean isSchedulesValid(CreateScheduleDto schedule1, CreateScheduleDto schedule2) {
        boolean result = false;

        LocalDateTime startA = createDate(schedule1.getStartTime(), schedule1.getDayWeek());
        LocalDateTime endA = createDate(schedule1.getEndTime(), schedule1.getDayWeek(), schedule1.getStartTime());

        LocalDateTime startB = createDate(schedule2.getStartTime(), schedule2.getDayWeek());
        LocalDateTime endB = createDate(schedule2.getEndTime(), schedule2.getDayWeek(), schedule2.getStartTime());

        if (
                isValidInterval(startA, endA)
                && isValidInterval(startB, endB)
        ) {
            LocalDateTime inicioShow1 = startA;
            LocalDateTime fimShow1 = endA;

            LocalDateTime inicioShow2 = startB;
            LocalDateTime fimShow2 = endB;

            if (
                    startA.isAfter(startB)
            ) {
                inicioShow1 = startB;
                fimShow1 = endB;

                inicioShow2 = startA;
                fimShow2 = endA;
            }

            if (
                    (inicioShow1.getDayOfWeek().equals(DayOfWeek.MONDAY)
                    && fimShow2.getDayOfWeek().equals(DayOfWeek.MONDAY))
                    && inicioShow1.getDayOfYear() != fimShow2.getDayOfYear()
            ) {
                fimShow1 = endA;
                inicioShow2 = startB;
                fimShow1 = fimShow1.minusDays(7);
            }

            if (
                    inicioShow2.isAfter(fimShow1)
                    && Duration.between(fimShow1, inicioShow2).toMinutes() >= 10
            ) {
                result = true;
            }
        }

        return result;
    }

    public static LocalDateTime createDate(TimeDto startTimeDto, int dayOfWeek) {
        LocalTime startTime = LocalTime.of(startTimeDto.getHour(), startTimeDto.getMinute());
        LocalDate day = LocalDate.of(2023, 4, 17 + dayOfWeek);
        return LocalDateTime.of(day, startTime);
    }

    public static LocalDateTime createDate(TimeDto endTimeDto, int dayOfWeek, TimeDto startTimeDto) {
        LocalTime startTime = LocalTime.of(startTimeDto.getHour(), startTimeDto.getMinute());
        LocalTime endTime = LocalTime.of(endTimeDto.getHour(), endTimeDto.getMinute());
        if (endTime.isBefore(startTime)) {
            dayOfWeek += 1;
        }
        LocalDate day = LocalDate.of(2023, 4, 17 + dayOfWeek);
        return LocalDateTime.of(day, endTime);
    }

    public static boolean isValidInterval(LocalDateTime start, LocalDateTime end) {
        return start.isBefore(end);
    }
}
