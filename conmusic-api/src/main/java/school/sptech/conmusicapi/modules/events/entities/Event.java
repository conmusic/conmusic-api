package school.sptech.conmusicapi.modules.events.entities;

import jakarta.persistence.*;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "evento")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", length = 45)
    private String name;

    @Column(name = "description", length = 45)
    private String description;

    @Column(name = "valor")
    private Double value;

    @Column(name = "taxa_cover")
    private Double coverCharge;

    @Column(name = "data_inicio")
    private LocalDateTime startDate;

    @Column(name = "data_termino")
    private LocalDateTime endDate;

    @Column(name = "unico")
    private Boolean isUnique;

    @ManyToOne
    @JoinColumn(name = "fk_estabelecimento")
    private Establishment establishment;

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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean getUnique() {
        return isUnique;
    }

    public void setUnique(Boolean unique) {
        isUnique = unique;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
