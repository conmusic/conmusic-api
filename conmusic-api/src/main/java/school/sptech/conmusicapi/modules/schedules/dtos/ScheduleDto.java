package school.sptech.conmusicapi.modules.schedules.dtos;

import school.sptech.conmusicapi.shared.dtos.TimeDto;

public class ScheduleDto {
    private Integer id;
    private Integer dayOfWeek;
    private TimeDto startTime;
    private TimeDto endTime;
    private Integer eventId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
