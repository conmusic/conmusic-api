package school.sptech.conmusicapi.modules.recurrence.entities;

import jakarta.persistence.*;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "recorrencia")
public class Recurrence {
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

    @OneToMany(mappedBy = "recurrence", fetch = FetchType.LAZY)
    List<Schedule> schedules;

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

    public List<Schedule> getSchedules() {
        return Objects.isNull(schedules)
                ? Collections.emptyList()
                : schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
