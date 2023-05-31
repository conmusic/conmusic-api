package school.sptech.conmusicapi.modules.recurrence.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.events.repositories.IEventRepository;
import school.sptech.conmusicapi.modules.manager.repositories.IManagerRepository;
import school.sptech.conmusicapi.modules.recurrence.dtos.CreateRecurrenceRulesDto;
import school.sptech.conmusicapi.modules.recurrence.dtos.RecurrenceRuleDto;
import school.sptech.conmusicapi.modules.recurrence.entities.Recurrence;
import school.sptech.conmusicapi.modules.recurrence.repositories.IRecurrenceRepository;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;
import school.sptech.conmusicapi.modules.schedules.repositories.IScheduleRepository;
import school.sptech.conmusicapi.modules.user.dtos.UserDetailsDto;
import school.sptech.conmusicapi.modules.user.entities.User;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;

import java.time.LocalDate;
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

    public void create(CreateRecurrenceRulesDto dto, Integer eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Event with id %d not found", eventId)
                ));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDto details = (UserDetailsDto) authentication.getPrincipal();

        User user = userRepository.findByEmail(details.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with email %s not found", details.getUsername())
                ));

        if (!event.getEstablishment().getManager().getId().equals(user.getId())) {
            throw new BusinessRuleException("This user is not allowed to update this event");
        }

        if (dto.getRules().isEmpty()) {
            throw new BusinessRuleException("No recurrence rules were given");
        }

        List<Schedule> schedules = new ArrayList<>();
        List<Recurrence> recurrences = new ArrayList<>();

        LocalDate today = LocalDate.now();
        for (RecurrenceRuleDto rule : dto.getRules()) {
            int days = (rule.getDayOfWeek().getValue() + 7 - today.getDayOfWeek().getValue()) % 7;

        }
    }
}
