package school.sptech.conmusicapi.modules.artist.dtos;

import school.sptech.conmusicapi.modules.gender.entities.Gender;
import school.sptech.conmusicapi.modules.user.dtos.UserDto;

import java.util.List;

public class ArtistDto extends UserDto {

    private List<Gender> musicalGenres;

    public List<Gender> getMusicalGenres() {
        return musicalGenres;
    }

    public void setMusicalGenres(List<Gender> musicalGenres) {
        this.musicalGenres = musicalGenres;
    }
}