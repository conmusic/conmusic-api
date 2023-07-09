package school.sptech.conmusicapi.modules.genre.dto;

import jakarta.validation.constraints.NotBlank;

public class DisplayingGenreDto {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
