package school.sptech.conmusicapi.modules.recurrence.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.util.List;

public class RecurrenceRuleDto {
    @NotNull
    private DayOfWeek dayOfWeek;

    @NotEmpty
    private List<@Valid TimetableDto> timetableDtos;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<TimetableDto> getTimetables() {
        return timetableDtos;
    }

    public void setTimetables(List<TimetableDto> timetableDtos) {
        this.timetableDtos = timetableDtos;
    }
}
