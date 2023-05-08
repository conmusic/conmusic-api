package school.sptech.conmusicapi.modules.artist.dtos;

import school.sptech.conmusicapi.modules.gender.entities.Gender;
import school.sptech.conmusicapi.modules.user.dtos.UserDto;

import java.util.List;

public class ArtistDto extends UserDto {

    private List<Gender> genders;

    public List<Gender> getGenders() {
        return genders;
    }

    public void setGenders(List<Gender> genders) {
        this.genders = genders;
    }
}