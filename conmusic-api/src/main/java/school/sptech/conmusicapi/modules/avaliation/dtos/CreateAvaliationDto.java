package school.sptech.conmusicapi.modules.avaliation.dtos;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.*;

public class CreateAvaliationDto {
    @Min(0)
    @Max(5)
    private Integer rating;

    @NotBlank
    @Size(max = 45)
    private String comentary;

    @NotBlank
    @Positive
    private Integer establishmentId;

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

    public Integer getEstablishmentId() {
        return establishmentId;
    }

    public void setEstablishmentId(Integer establishmentId) {
        this.establishmentId = establishmentId;
    }

}
