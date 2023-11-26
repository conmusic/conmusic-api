package school.sptech.conmusicapi.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.events.repositories.IEventRepository;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;
import school.sptech.conmusicapi.modules.schedules.repositories.IScheduleRepository;
import school.sptech.conmusicapi.modules.schedules.utils.ScheduleUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class ScheduleCreatingJob {
    @Autowired
    private IScheduleRepository scheduleRepository;
    @Autowired
    private IEventRepository eventRepository;

    @Scheduled(cron = "0 0 1 * * *")
    public void run() {
        LocalDateTime today = LocalDateTime.now();

        System.out.println("["+ this.getClass().getSimpleName() + "]: Starting at " + today);
        int createdSchedules = 0;

        List<Event> events = eventRepository.findAll();
        System.out.println(String.format("Found %d events", events.size()));
        for (Event event : events) {
            List<Schedule> futureSchedules = scheduleRepository.findByEventIdAndStartDateTimeOrEndDateTimeIsAfter(event.getId(), today);

            LocalDateTime startDateTime = today.plusDays(1).withHour(randomInteger(9, 20));
            LocalDateTime endDateTime = startDateTime.plusHours(randomInteger(1, 3));

            Schedule schedule = new Schedule();
            schedule.setStartDateTime(startDateTime);
            schedule.setEndDateTime(endDateTime);
            schedule.setEvent(event);
            schedule.setConfirmed(false);

            boolean validSchedule = futureSchedules.stream().noneMatch(s -> ScheduleUtil.isScheduleOverlappingOtherSchedule(s, schedule));

            if (validSchedule) {
                scheduleRepository.save(schedule);
                createdSchedules++;
            }
        }

        System.out.println(String.format("Created %d schedules", createdSchedules));
        System.out.println(String.format("["+ this.getClass().getSimpleName() + "] finished at %s", LocalDateTime.now()));
    }

    private int randomInteger(int start, int end) {
        return ThreadLocalRandom.current().nextInt(start, end + 1);
    }
}
