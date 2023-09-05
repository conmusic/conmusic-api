package school.sptech.conmusicapi.modules.avaliation.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateAvaliationDto {
    @Min(0)
    @Max(5)
    private Integer rating;

    @NotBlank
    @Size(max = 45)
    private String comentary;

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
}
