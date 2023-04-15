package school.sptech.conmusicapi.modules.event.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.event.dtos.CreateEventDto;
import school.sptech.conmusicapi.modules.event.dtos.CreateScheduleDto;
import school.sptech.conmusicapi.modules.event.dtos.EventDto;
import school.sptech.conmusicapi.modules.event.dtos.ScheduleDto;
import school.sptech.conmusicapi.modules.event.entities.Event;
import school.sptech.conmusicapi.modules.event.mapper.EventMapper;
import school.sptech.conmusicapi.modules.event.repositories.IEventRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private IEventRepository eventRepository;

    public Optional<EventDto> create(CreateEventDto dto) {

        if (dto.getValue() == null && dto.getCoverCharge() == null){
            return Optional.empty();
        }

        boolean scheduleInvalido = false;
        if (dto.getSchedule().size() > 1) {
            for (CreateScheduleDto schedule: dto.getSchedule()) {
                LocalDate diaA = LocalDate.of(2023, 4, 17 + schedule.getDayWeek());
                LocalTime horarioA1 = schedule.getEndTime().plusMinutes(9);

                LocalDateTime a0 = LocalDateTime.of(diaA, schedule.getStartTime());
                LocalDateTime a1 = LocalDateTime.of(diaA, horarioA1);

                for (CreateScheduleDto schedule2: dto.getSchedule()) {
                    LocalDate diaB = LocalDate.of(2023, 4, 17 + schedule2.getDayWeek());
                    LocalTime horarioB1 = schedule2.getEndTime().plusMinutes(9);

                    LocalDateTime b0 = LocalDateTime.of(diaB, schedule2.getStartTime());
                    LocalDateTime b1 = LocalDateTime.of(diaB, horarioB1);

                    if (b1.isAfter(b0) || !b0.isAfter(a0) || !b0.isAfter(a1)) {
                        scheduleInvalido = true;
                        break;
                    }
                }

                if (scheduleInvalido) {
                    break;
                }
            }
        }
        if (scheduleInvalido){
            return Optional.empty();
        }
        Event createdEvent = eventRepository.save(EventMapper.fromDto(dto));
        return Optional.of(EventMapper.toDto(createdEvent));
    }
}
