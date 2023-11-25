package school.sptech.conmusicapi.modules.events.dtos;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import school.sptech.conmusicapi.modules.genre.dto.DisplayingGenreDto;

@FilterDef(name = "deletedEventsDtoFilter", parameters = @ParamDef(name = "isDeleted", type =boolean.class))
@Filter(name = "deletedEventsDtoFilter", condition = "deleted = :isDeleted")
public class DisplayEstablishmentEventDto {
    private Integer id;
    private String name;
    private String description;
    private Double value;
    private Double coverCharge;
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

    public DisplayingGenreDto getGenre() {
        return genre;
    }

    public void setGenre(DisplayingGenreDto genre) {
        this.genre = genre;
    }
}
