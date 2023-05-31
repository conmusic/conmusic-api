package school.sptech.conmusicapi.modules.shedule.service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.events.repositories.IEventRepository;
import school.sptech.conmusicapi.modules.schedules.dtos.CreateScheduleDto;
import school.sptech.conmusicapi.modules.schedules.dtos.ScheduleDto;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;
import school.sptech.conmusicapi.modules.schedules.repositories.IScheduleRepository;
import school.sptech.conmusicapi.modules.schedules.services.ScheduleService;
import school.sptech.conmusicapi.modules.schedules.utils.ScheduleUtil;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @Mock
    private IScheduleRepository scheduleRepository;

    @Mock
    private IEventRepository eventRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    @Test
    @DisplayName("create - Throw exception when event is not found")
    void createShouldThrowExceptionWhenEventIsNotFound() {
        // given
        int eventId = 1;
        String errorMessage = "Event with id 1 was not found";

        CreateScheduleDto dto = new CreateScheduleDto();
        dto.setStartDateTime(LocalDateTime.now());
        dto.setEndDateTime(LocalDateTime.now().plusHours(1));

        // when
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        // then
        EntityNotFoundException result = assertThrows(EntityNotFoundException.class, () -> scheduleService.create(dto, eventId));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(EntityNotFoundException.class, result);
    }

    @Test
    @DisplayName("create - Throw exception when schedule overlaps with other schedule")
    void createShouldThrowExceptionWhenScheduleOverlapsWithOtherSchedule() {
        // given
        int eventId = 1;
        String errorMessage = "Schedule is overlapping other schedule.";

        CreateScheduleDto dto = new CreateScheduleDto();
        dto.setStartDateTime(LocalDateTime.now());
        dto.setEndDateTime(LocalDateTime.now().plusHours(1));

        Event event = new Event();
        event.setId(eventId);

        Schedule existingSchedule = new Schedule();
        existingSchedule.setStartDateTime(LocalDateTime.now().minusMinutes(30));
        existingSchedule.setEndDateTime(LocalDateTime.now().plusMinutes(30));

        List<Schedule> schedules = new ArrayList<>();
        schedules.add(existingSchedule);
        event.setSchedules(schedules);

        // when
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        // then
        BusinessRuleException result = assertThrows(BusinessRuleException.class, () -> scheduleService.create(dto, eventId));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(BusinessRuleException.class, result);
    }


}
