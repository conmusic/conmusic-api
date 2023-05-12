package school.sptech.conmusicapi.modules.genre.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterGenreDto {

    @NotBlank
    @Size(min = 3, max = 55)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
