package school.sptech.conmusicapi.modules.events.entities;

import jakarta.persistence.*;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.genre.entities.Genre;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "evento")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", length = 45)
    private String name;

    @Column(name = "descricao", length = 45)
    private String description;

    @Column(name = "valor")
    private Double value;

    @Column(name = "taxa_cover")
    private Double coverCharge;

    @ManyToOne
    @JoinColumn(name = "fk_estabelecimento")
    private Establishment establishment;

    @ManyToOne
    @JoinColumn(name = "fk_genero")
    private Genre genre;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<Schedule> schedules;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Schedule> getSchedules() {
        return Objects.isNull(schedules) ? Collections.emptyList() : schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
