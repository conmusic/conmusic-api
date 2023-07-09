package school.sptech.conmusicapi.modules.recurrence.util;

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
}
