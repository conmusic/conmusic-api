package school.sptech.conmusicapi.modules.events.dtos;

import jakarta.validation.constraints.*;

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

    @NotNull
    @Positive
    private Integer establishmentId;

    @NotBlank
    @Size(min = 3, max = 55)
    private String genre;

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

    public Integer getEstablishmentId() {
        return establishmentId;
    }

    public void setEstablishmentId(Integer establishmentId) {
        this.establishmentId = establishmentId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
