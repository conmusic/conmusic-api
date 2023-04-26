package school.sptech.conmusicapi.modules.event.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import school.sptech.conmusicapi.shared.dtos.TimeDto;

public class CreateScheduleDto {
    @NotNull
    private Integer dayWeek;

    @NotNull
    private TimeDto startTime;

    @NotNull
    private TimeDto endTime;

    @Positive
    private Integer eventId;

    public Integer getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(Integer dayWeek) {
        this.dayWeek = dayWeek;
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
