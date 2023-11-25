package school.sptech.conmusicapi.modules.avaliation.dtos;

import jakarta.validation.constraints.*;
import school.sptech.conmusicapi.modules.artist.dtos.ArtistDto;
import school.sptech.conmusicapi.modules.establishment.dtos.EstablishmentDto;
import school.sptech.conmusicapi.modules.show.dtos.ShowDto;

public class AvaliationDto {

    private Integer id;

    private Double rating;

    private String comentary;

    private ArtistDto artistDto;

    private EstablishmentDto establishmentDto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComentary() {
        return comentary;
    }

    public void setComentary(String comentary) {
        this.comentary = comentary;
    }

    public ArtistDto getArtistDto() {
        return artistDto;
    }

    public void setArtistDto(ArtistDto artistDto) {
        this.artistDto = artistDto;
    }

    public EstablishmentDto getEstablishmentDto() {
        return establishmentDto;
    }

    public void setEstablishmentDto(EstablishmentDto establishmentDto) {
        this.establishmentDto = establishmentDto;
    }
}
