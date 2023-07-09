package school.sptech.conmusicapi.modules.events.dtos;

import school.sptech.conmusicapi.modules.establishment.dtos.DisplayScheduleEstablishmentDto;
import school.sptech.conmusicapi.modules.genre.dto.DisplayingGenreDto;

public class DisplayScheduleEventDto {
    private Integer id;
    private String name;
    private String description;
    private Double value;
    private Double coverCharge;
    private DisplayScheduleEstablishmentDto establishment;
    private DisplayingGenreDto genre;

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

    public DisplayScheduleEstablishmentDto getEstablishment() {
        return establishment;
    }

    public void setEstablishment(DisplayScheduleEstablishmentDto establishment) {
        this.establishment = establishment;
    }

    public DisplayingGenreDto getGenre() {
        return genre;
    }

    public void setGenre(DisplayingGenreDto genre) {
        this.genre = genre;
    }
}
