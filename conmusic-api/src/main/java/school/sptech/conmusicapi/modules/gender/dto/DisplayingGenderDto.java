package school.sptech.conmusicapi.modules.gender.dto;

import jakarta.validation.constraints.NotBlank;

public class DisplayingGenderDto {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
