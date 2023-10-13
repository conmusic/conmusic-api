package school.sptech.conmusicapi.modules.recurrence.util;

import school.sptech.conmusicapi.modules.recurrence.entities.Recurrence;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;
import school.sptech.conmusicapi.modules.schedules.utils.ScheduleUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RecurrenceUtil {
    public static boolean isRecurrenceOverlappingWithOtherRecurrence(
            LocalDateTime start1,
            LocalDateTime end1,
            LocalDateTime start2,
            LocalDateTime end2
    ) {
        boolean isRecurrence1BeforeRecurrence2 =
                start1.isBefore(start2)
                        && start1.isBefore(end2)
                        && end1.isBefore(start2)
                        && end1.isBefore(end2);

        boolean isRecurrence1AfterRecurrence2 =
                start1.isAfter(start2)
                        && start1.isAfter(end2)
                        && end1.isAfter(start2)
                        && end1.isAfter(end2);

        return !isRecurrence1BeforeRecurrence2 && !isRecurrence1AfterRecurrence2;
    }


    public static boolean isRecurrenceOverlappingOtherRecurrence(Recurrence r1, Recurrence r2) {
        LocalDate today = LocalDate.now();
        Schedule s1 = ScheduleUtil.createScheduleForRecurrence(r1, today);
        Schedule s2 = ScheduleUtil.createScheduleForRecurrence(r2, today);
        return ScheduleUtil.isDurationIntervalInvalid(s1) || ScheduleUtil.isDurationIntervalInvalid(s2) || ScheduleUtil.isScheduleOverlappingOtherSchedule(s1, s2);
    }

}
