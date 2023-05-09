package school.sptech.conmusicapi.modules.events.dtos;

import school.sptech.conmusicapi.modules.establishment.dtos.EstablishmentDto;

import java.time.LocalDateTime;

public class EventDto {
    private Integer id;
    private String name;
    private String description;
    private Double value;
    private Double coverCharge;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isUnique;
    private EstablishmentDto establishment;

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

    public EstablishmentDto getEstablishment() {
        return establishment;
    }

    public void setEstablishment(EstablishmentDto establishment) {
        this.establishment = establishment;
    }
}
