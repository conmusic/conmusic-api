package school.sptech.conmusicapi.modules.events.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class CreateEventDto {
    @NotBlank
    @Size(min = 5, max = 45)
    private String name;

    @NotBlank
    @Size(max = 45)
    private String description;

    @Min(200)
    private Double value;

    @Max(100)
    private Double coverCharge;

    @Future
    private LocalDateTime startDate;

    @Future
    private LocalDateTime endDate;

    private Boolean isUnique;

    @NotNull
    @Positive
    private Integer establishmentId;

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

    public Integer getEstablishmentId() {
        return establishmentId;
    }

    public void setEstablishmentId(Integer establishmentId) {
        this.establishmentId = establishmentId;
    }
}
