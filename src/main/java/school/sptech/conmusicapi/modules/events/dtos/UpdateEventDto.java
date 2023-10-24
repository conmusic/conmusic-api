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
    private DisplayScheduleEstablishmentDto establishment;
    private Genre genre;
    private List<Schedule> schedules;

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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
