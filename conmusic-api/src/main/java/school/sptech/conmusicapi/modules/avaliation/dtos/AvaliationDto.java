package school.sptech.conmusicapi.modules.avaliation.dtos;

import jakarta.validation.constraints.*;
import school.sptech.conmusicapi.modules.establishment.dtos.EstablishmentDto;
import school.sptech.conmusicapi.modules.show.dtos.ShowDto;

public class AvaliationDto {

    private Integer id;

    private Integer rating;

    private String comentary;

    private EstablishmentDto establishment;

    private ShowDto show;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComentary() {
        return comentary;
    }

    public void setComentary(String comentary) {
        this.comentary = comentary;
    }

    public EstablishmentDto getEstablishment() {
        return establishment;
    }

    public void setEstablishment(EstablishmentDto establishment) {
        this.establishment = establishment;
    }

    public ShowDto getShow() {
        return show;
    }

    public void setShow(ShowDto show) {
        this.show = show;
    }
}
