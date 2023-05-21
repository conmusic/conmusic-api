package school.sptech.conmusicapi.modules.schedules.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.events.repositories.IEventRepository;
import school.sptech.conmusicapi.modules.schedules.dtos.CreateScheduleDto;
import school.sptech.conmusicapi.modules.schedules.dtos.ScheduleDto;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;
import school.sptech.conmusicapi.modules.schedules.mappers.ScheduleMapper;
import school.sptech.conmusicapi.modules.schedules.repositories.IScheduleRepository;
import school.sptech.conmusicapi.modules.schedules.utils.ScheduleUtil;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;

@Service
public class ScheduleService {
    @Autowired
    private IEventRepository eventRepository;

    @Autowired
    private IScheduleRepository scheduleRepository;

    public ScheduleDto create(CreateScheduleDto dto, Integer eventId) {
        Schedule schedule = ScheduleMapper.fromDto(dto);

        if (ScheduleUtil.isDurationIntervalInvalid(schedule)) {
            throw new BusinessRuleException("Schedule with invalid dates. Start DateTime MUST be before End DateTime");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Event with id %d was not found", eventId)));

        schedule.setEvent(event);

        for (Schedule s : event.getSchedules()) {
            if (ScheduleUtil.isScheduleOverlappingOtherSchedule(schedule, s)) {
                throw new BusinessRuleException("Schedule is overlapping other schedule.");
            }
        }

        Schedule createdSchedule = scheduleRepository.save(schedule);
        return ScheduleMapper.toDto(createdSchedule);
    }
}
