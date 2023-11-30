package school.sptech.conmusicapi.modules.events.dtos;

import school.sptech.conmusicapi.modules.establishment.dtos.DisplayScheduleEstablishmentDto;
import school.sptech.conmusicapi.modules.genre.dto.DisplayingGenreDto;
import school.sptech.conmusicapi.modules.genre.entities.Genre;
import school.sptech.conmusicapi.modules.schedules.dtos.BasicScheduleDto;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;

import java.util.List;

public class UpdateEventDto {
    private String name;
    private String description;
    private Double value;
    private Double coverCharge;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
