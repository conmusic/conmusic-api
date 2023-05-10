package school.sptech.conmusicapi.modules.schedules.entities;

import jakarta.persistence.*;
import school.sptech.conmusicapi.modules.events.entities.Event;

import java.time.LocalTime;

@Entity
@Table(name = "agenda")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dia_semana")
    private Integer dayOfWeek;

    @Column(name = "horario_inicio")
    private LocalTime startTime;

    @Column(name = "horario_termino")
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "fk_evento")
    private Event event;

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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
