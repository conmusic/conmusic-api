package school.sptech.conmusicapi.modules.schedules.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import school.sptech.conmusicapi.modules.events.entities.Event;

import java.time.LocalDateTime;

@Entity
@Table(name = "agenda")
@FilterDef(name = "deletedScheduleFilter", parameters = @ParamDef(name = "isDeleted", type =boolean.class))
@Filter(name = "deletedScheduleFilter", condition = "deleted = :isDeleted")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_inicio")
    private LocalDateTime startDateTime;

    @Column(name = "data_termino")
    private LocalDateTime endDateTime;

    @Column(name = "confirmado")
    private Boolean confirmed;

    private Boolean deleted  = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "fk_evento")
    private Event event;

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

    public Boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
