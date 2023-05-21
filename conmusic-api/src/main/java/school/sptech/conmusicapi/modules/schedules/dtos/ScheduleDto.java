package school.sptech.conmusicapi.modules.schedules.dtos;

import school.sptech.conmusicapi.modules.events.dtos.DisplayScheduleEventDto;

import java.time.LocalDateTime;

public class ScheduleDto {
    private Integer id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Boolean confirmed;
    private DisplayScheduleEventDto event;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public DisplayScheduleEventDto getEvent() {
        return event;
    }

    public void setEvent(DisplayScheduleEventDto event) {
        this.event = event;
    }
}
