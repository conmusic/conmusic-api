package school.sptech.conmusicapi.modules.schedules.dtos;

import java.time.LocalDateTime;

public class ReadScheduleDto {
    private Integer eventId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
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
}
