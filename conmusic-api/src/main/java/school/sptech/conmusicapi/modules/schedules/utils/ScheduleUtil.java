package school.sptech.conmusicapi.modules.schedules.utils;

import school.sptech.conmusicapi.modules.recurrence.entities.Recurrence;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ScheduleUtil {
    private final static int MIN_SCHEDULE_DURATION = 10;

    public static Boolean isScheduleOverlappingOtherSchedule(Schedule schedule1, Schedule schedule2) {
        boolean isSchedule1BeforeSchedule2 =
                schedule1.getStartDateTime().isBefore(schedule2.getStartDateTime())
                        && schedule1.getStartDateTime().isBefore(schedule2.getEndDateTime())
                        && schedule1.getEndDateTime().isBefore(schedule2.getStartDateTime())
                        && schedule1.getEndDateTime().isBefore(schedule2.getEndDateTime());

        boolean isSchedule1AfterSchedule2 =
                schedule1.getStartDateTime().isAfter(schedule2.getStartDateTime())
                        && schedule1.getStartDateTime().isAfter(schedule2.getEndDateTime())
                        && schedule1.getEndDateTime().isAfter(schedule2.getStartDateTime())
                        && schedule1.getEndDateTime().isAfter(schedule2.getEndDateTime());

        return !isSchedule1BeforeSchedule2 && !isSchedule1AfterSchedule2;
    }

    public static Boolean isDurationIntervalInvalid(Schedule schedule) {
        return Duration.between(schedule.getStartDateTime(), schedule.getEndDateTime()).toMinutes() < MIN_SCHEDULE_DURATION
                && (schedule.getStartDateTime().isAfter(schedule.getEndDateTime())
                || schedule.getStartDateTime().equals(schedule.getEndDateTime()));
    }

    public static Schedule createScheduleForRecurrence(Recurrence r, LocalDate date) {
        int dayUntilTarget = (r.getDayOfWeek().ordinal() + date.getDayOfWeek().ordinal() + 7) % 7;
        LocalDate scheduleDate = date.plusDays(dayUntilTarget > 0 ? dayUntilTarget : 7);
        LocalDateTime startDateTime = scheduleDate.atTime(r.getStartTime());
        LocalDateTime endDateTime = scheduleDate.atTime(r.getEndTime());
        endDateTime = endDateTime.isBefore(startDateTime) ? endDateTime.plusDays(1) : endDateTime;
        Schedule schedule = new Schedule();
        schedule.setStartDateTime(startDateTime);
        schedule.setEndDateTime(endDateTime);
        return schedule;
    }

}