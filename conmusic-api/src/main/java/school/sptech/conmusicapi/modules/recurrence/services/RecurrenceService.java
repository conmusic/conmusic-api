package school.sptech.conmusicapi.modules.recurrence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.events.repositories.IEventRepository;
import school.sptech.conmusicapi.modules.recurrence.dtos.CreateRecurrenceRulesDto;
import school.sptech.conmusicapi.modules.recurrence.dtos.RecurrenceRuleDto;
import school.sptech.conmusicapi.modules.recurrence.dtos.TimetableDto;
import school.sptech.conmusicapi.modules.recurrence.entities.Recurrence;
import school.sptech.conmusicapi.modules.recurrence.repositories.IRecurrenceRepository;
import school.sptech.conmusicapi.modules.recurrence.util.DayOfWeek;
import school.sptech.conmusicapi.modules.recurrence.util.RecurrenceUtil;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;
import school.sptech.conmusicapi.modules.schedules.repositories.IScheduleRepository;
import school.sptech.conmusicapi.modules.schedules.utils.ScheduleUtil;
import school.sptech.conmusicapi.modules.user.dtos.UserDetailsDto;
import school.sptech.conmusicapi.modules.user.entities.User;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecurrenceService {
    @Autowired
    private IRecurrenceRepository recurrenceRepository;

    @Autowired
    private IScheduleRepository scheduleRepository;

    @Autowired
    private IEventRepository eventRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private RecurrenceUtil recurrenceUtil; // Injete o RecurrenceUtil

    public void create(CreateRecurrenceRulesDto dto, Integer eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Evento com ID %d não encontrado", eventId)
                ));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDto details = (UserDetailsDto) authentication.getPrincipal();

        User user = userRepository.findByEmail(details.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Usuário com email %s não encontrado", details.getUsername())
                ));

        if (!event.getEstablishment().getManager().getId().equals(user.getId())) {
            throw new BusinessRuleException("Este usuário não está autorizado a atualizar este evento");
        }

        if (dto.getRules().isEmpty()) {
            throw new BusinessRuleException("Nenhuma regra de recorrência foi fornecida");
        }

        LocalDate today = LocalDate.now();

        List<Schedule> existingSchedules = scheduleRepository.findDetachedByEventIdAndScheduleAfter(eventId, today);
        List<Recurrence> existingRecurrences = recurrenceRepository.findByEventId(eventId);

        List<Recurrence> recurrences = new ArrayList<>();
        for (RecurrenceRuleDto rule : dto.getRules()) {
            for (TimetableDto timetable : rule.getTimetables()) {
                DayOfWeek targetDayOfWeek = rule.getDayOfWeek();

                int daysUntilTarget = (targetDayOfWeek.ordinal() - today.getDayOfWeek().ordinal() + 7) % 7;
                LocalDate scheduleDate = today.plusDays(daysUntilTarget > 0 ? daysUntilTarget : 7);

                Recurrence recurrence = new Recurrence();
                recurrence.setEvent(event);
                recurrence.setDayOfWeek(targetDayOfWeek);
                recurrence.setStartTime(timetable.getStartTime());
                recurrence.setEndTime(timetable.getEndTime());
                recurrence.setSchedules(new ArrayList<>());

                while (!scheduleDate.isAfter(dto.getEndRecurrence())) {
                    LocalDateTime startDateTime = scheduleDate.atTime(LocalTime.from(timetable.getStartTime()));
                    LocalDateTime endDateTime = scheduleDate.atTime(LocalTime.from(timetable.getEndTime()));

                    endDateTime = endDateTime.isBefore(startDateTime)
                            ? endDateTime.plusDays(1)
                            : endDateTime;



                    Schedule schedule = new Schedule();
                    schedule.setStartDateTime(startDateTime);
                    schedule.setEndDateTime(endDateTime);
                    schedule.setConfirmed(false);
                    schedule.setEvent(event);

                    if (ScheduleUtil.isDurationIntervalInvalid(schedule)) {
                        throw new BusinessRuleException("Agendamento com datas inválidas. O horário de início DEVE ser antes do horário de término");
                    }

                    if (existingRecurrences.stream().anyMatch(r -> recurrenceUtil.isRecurrenceOverlappingOtherRecurrence(r, recurrence))) {
                        throw new BusinessRuleException("O agendamento está se sobrepondo a outro agendamento.");
                    }

                    existingSchedules.add(schedule);
                    recurrence.getSchedules().add(schedule);
                    scheduleDate = scheduleDate.plusWeeks(1);
                }

                recurrences.add(recurrence);
            }
        }
        recurrenceRepository.saveAll(recurrences);
    }
}

