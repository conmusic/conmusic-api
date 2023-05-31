package school.sptech.conmusicapi.modules.recurrence.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class TimetableDto {
    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
