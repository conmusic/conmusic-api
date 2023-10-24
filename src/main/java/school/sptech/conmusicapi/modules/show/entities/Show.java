package school.sptech.conmusicapi.modules.show.entities;

import jakarta.persistence.*;
import school.sptech.conmusicapi.modules.artist.entities.Artist;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;

@Entity
@Table(name = "show")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated()
    @Column(name = "status")
    private ShowStatusEnum status;

    @Column(name = "valor")
    private Double value;

    @Column(name = "taxa_cover")
    private Double coverCharge;

    @ManyToOne
    @JoinColumn(name = "fk_evento")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "fk_artista")
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "fk_agenda")
    private Schedule schedule;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ShowStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ShowStatusEnum status) {
        this.status = status;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getCoverCharge() {
        return coverCharge;
    }

    public void setCoverCharge(Double coverCharge) {
        this.coverCharge = coverCharge;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
