package school.sptech.conmusicapi.modules.events.dtos;

import school.sptech.conmusicapi.modules.establishment.dtos.EstablishmentDto;
import school.sptech.conmusicapi.modules.genre.dto.DisplayingGenreDto;
import school.sptech.conmusicapi.modules.schedules.dtos.BasicScheduleDto;

import java.util.List;

public class EventDto {
    private Integer id;
    private String name;
    private String description;
    private Double value;
    private Double coverCharge;
    private EstablishmentDto establishment;
    private DisplayingGenreDto genre;
    private List<BasicScheduleDto> schedules;

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

    public EstablishmentDto getEstablishment() {
        return establishment;
    }

    public void setEstablishment(EstablishmentDto establishment) {
        this.establishment = establishment;
    }

    public DisplayingGenreDto getGenre() {
        return genre;
    }

    public void setGenre(DisplayingGenreDto genre) {
        this.genre = genre;
    }

    public List<BasicScheduleDto> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<BasicScheduleDto> schedules) {
        this.schedules = schedules;
    }
}