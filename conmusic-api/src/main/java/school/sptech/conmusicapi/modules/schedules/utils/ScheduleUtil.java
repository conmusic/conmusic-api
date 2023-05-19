package school.sptech.conmusicapi.modules.schedules.utils;

import school.sptech.conmusicapi.modules.schedules.entities.Schedule;

import java.time.*;

public class ScheduleUtil {
    public static Boolean isScheduleValid(Schedule schedule1, Schedule schedule2) {
        boolean result = false;

        // Creating fake valid shows
        LocalDateTime startDateTimeA = createDate(schedule1.getStartTime(), schedule1.getDayOfWeek());
        LocalDateTime endDateTimeA = createDate(schedule1.getEndTime(), schedule1.getDayOfWeek(), schedule1.getStartTime());

        LocalDateTime startDateTimeB = createDate(schedule2.getStartTime(), schedule2.getDayOfWeek());
        LocalDateTime endDateTimeB = createDate(schedule2.getEndTime(), schedule2.getDayOfWeek(), schedule2.getStartTime());

        // Verifying if start and end are valid
        if (
            isValidInterval(startDateTimeA, endDateTimeA)
            && isValidInterval(startDateTimeB, endDateTimeB)
        ) {
            LocalDateTime startShow1 = startDateTimeA;
            LocalDateTime endShow1 = endDateTimeA;

            LocalDateTime startShow2 = startDateTimeB;
            LocalDateTime endShow2 = endDateTimeB;

            // Sorting show by date
            if (
                startDateTimeA.isAfter(startDateTimeB)
            ) {
                startShow1 = startDateTimeB;
                endShow1 = endDateTimeB;

                startShow2 = startDateTimeA;
                endShow2 = endDateTimeA;
            }

            // Validating when overlapping when show happens between two days
            if (
                (startShow1.getDayOfWeek().equals(DayOfWeek.MONDAY)
                    && endShow2.getDayOfWeek().equals(DayOfWeek.MONDAY))
                    && startShow1.getDayOfYear() != endShow2.getDayOfYear()
            ) {
                endShow1 = endDateTimeA;
                startShow2 = startDateTimeB;
                endShow1 = endShow1.minusDays(7);
            }

            // Verifying if one show is after the other and has at least 10 minutes of difference
            if (
                startShow2.isAfter(endShow1)
                    && Duration.between(endShow1, startShow2).toMinutes() >= 10
            ) {
                result = true;
            }
        }

        return result;
    }

    public static boolean isValidInterval(LocalDateTime start, LocalDateTime end) {
        return start.isBefore(end);
    }

    private static LocalDateTime createDate(LocalTime startTime, Integer dayOfWeek) {
        LocalDate day = LocalDate.of(2023, 4, 17 + dayOfWeek);
        return LocalDateTime.of(day, startTime);
    }

    private static LocalDateTime createDate(LocalTime startTime, Integer dayOfWeek, LocalTime endTime) {
        if (endTime.isBefore(startTime)) {
            dayOfWeek += 1;
        }
        LocalDate day = LocalDate.of(2023, 4, 17 + dayOfWeek);
        return LocalDateTime.of(day, endTime);
    }
}
