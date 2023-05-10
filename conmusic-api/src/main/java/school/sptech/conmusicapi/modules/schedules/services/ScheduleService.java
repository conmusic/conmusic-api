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

import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    private IEventRepository eventRepository;

    @Autowired
    private IScheduleRepository scheduleRepository;

    public ScheduleDto create(CreateScheduleDto dto, Integer eventId) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);

        if (eventOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("Event with id %d was not found", eventId));
        }

        Schedule schedule = ScheduleMapper.fromDto(dto);
        schedule.setEvent(eventOpt.get());

        Boolean isNewScheduleInvalid = false;
        for (Schedule s : eventOpt.get().getSchedules()) {
            if (ScheduleUtil.isScheduleValid(schedule, s)) {
                isNewScheduleInvalid = true;
                break;
            }
        }

        if (isNewScheduleInvalid) {
            throw new BusinessRuleException("Schedule is invalid due to overlapping or invalid interval");
        }

        Schedule createdSchedule = scheduleRepository.save(schedule);
        return ScheduleMapper.toDto(createdSchedule);
    }
}
