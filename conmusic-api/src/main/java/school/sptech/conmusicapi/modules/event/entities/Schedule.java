package school.sptech.conmusicapi.modules.event.entities;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "agenda")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dia_semana", precision = 1)
    private Integer dayWeek;

    @Column(name = "horario_inicio")
    private LocalTime startTime;

    @Column(name = "horario_termino")
    private LocalTime endTime;

    @ManyToOne
    private Event event;

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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }


}
