package school.sptech.conmusicapi.modules.schedules.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import school.sptech.conmusicapi.shared.dtos.TimeDto;

public class CreateScheduleDto {
    @NotNull
    @Min(0)
    @Max(6)
    private Integer dayOfWeek;

    @NotNull
    private TimeDto startTime;

    @NotNull
    private TimeDto endTime;

    @Positive
    private Integer eventId;

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public TimeDto getStartTime() {
        return startTime;
    }

    public void setStartTime(TimeDto startTime) {
        this.startTime = startTime;
    }

    public TimeDto getEndTime() {
        return endTime;
    }

    public void setEndTime(TimeDto endTime) {
        this.endTime = endTime;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
}
