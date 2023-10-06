package school.sptech.conmusicapi.modules.recurrence.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import school.sptech.conmusicapi.modules.recurrence.util.DayOfWeek;

import java.util.List;

public class RecurrenceRuleDto {
    private DayOfWeek dayOfWeek;

    @NotEmpty
    private List<@Valid TimetableDto> timetableDtos;

    public school.sptech.conmusicapi.modules.recurrence.util.DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(school.sptech.conmusicapi.modules.recurrence.util.DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<TimetableDto> getTimetables() {
        return timetableDtos;
    }

    public void setTimetables(List<TimetableDto> timetableDtos) {
        this.timetableDtos = timetableDtos;
    }
}
