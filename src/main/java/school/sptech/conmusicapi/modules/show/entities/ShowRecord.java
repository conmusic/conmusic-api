package school.sptech.conmusicapi.modules.show.entities;

import jakarta.persistence.*;
import school.sptech.conmusicapi.modules.show.util.LifeCycleEnum;
import school.sptech.conmusicapi.modules.show.util.RecordTypeEnum;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;
import school.sptech.conmusicapi.modules.user.entities.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_agenda")
public class ShowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated
    @Column(name = "status")
    private ShowStatusEnum status;

    @Enumerated
    @Column(name = "tipo")
    private RecordTypeEnum recordType;

    @Enumerated
    @Column(name = "ciclo_vida")
    private LifeCycleEnum lifeCycle;

    @Column(name = "data_inicio")
    private LocalDateTime startDateTime;

    @Column(name = "data_termino")
    private LocalDateTime endDateTime;

    @Column(name = "valor")
    private Double value;

    @Column(name = "taxa_cover")
    private Double coverCharge;

    @Column(name = "data_acao")
    private LocalDateTime dateAction;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private User user;

    @ManyToOne
    @JoinColumn(name = "fk_show")
    private Show show;

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

    public RecordTypeEnum getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordTypeEnum recordType) {
        this.recordType = recordType;
    }

    public LifeCycleEnum getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(LifeCycleEnum lifeCycle) {
        this.lifeCycle = lifeCycle;
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

    public LocalDateTime getDateAction() {
        return dateAction;
    }

    public void setDateAction(LocalDateTime dateAction) {
        this.dateAction = dateAction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
