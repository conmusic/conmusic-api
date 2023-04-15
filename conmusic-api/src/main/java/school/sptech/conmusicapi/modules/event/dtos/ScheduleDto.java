package school.sptech.conmusicapi.modules.event.dtos;

import java.time.LocalTime;

public class ScheduleDto {

    private Integer id;

    private Integer dayWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer eventId;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getDayWeek() {
        return dayWeek;
    }


    public void setDayWeek(Integer dayWeek) {
        this.dayWeek = dayWeek;
    }


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

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
}
