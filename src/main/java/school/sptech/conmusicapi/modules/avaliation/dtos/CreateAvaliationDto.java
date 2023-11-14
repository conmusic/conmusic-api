package school.sptech.conmusicapi.modules.avaliation.dtos;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.*;

public class CreateAvaliationDto {
    @Min(0)
    @Max(5)
    private Double rating;

    @Size(max = 45)
    private String comentary;

    @NotNull
    @Positive
    private Integer artistId;

    @NotNull
    @Positive
    private Integer establishmentId;

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

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public Integer getEstablishmentId() {
        return establishmentId;
    }

    public void setEstablishmentId(Integer establishmentId) {
        this.establishmentId = establishmentId;
    }
}
