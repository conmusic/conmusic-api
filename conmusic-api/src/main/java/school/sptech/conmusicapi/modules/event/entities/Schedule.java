package school.sptech.conmusicapi.modules.event.entities;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer dayWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    @ManyToOne
    private Evento event;

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

    public Evento getEvent() {
        return event;
    }

    public void setEvent(Evento event) {
        this.event = event;
    }


}
