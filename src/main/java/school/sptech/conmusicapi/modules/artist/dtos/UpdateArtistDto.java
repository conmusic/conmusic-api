package school.sptech.conmusicapi.modules.artist.dtos;

import school.sptech.conmusicapi.modules.user.dtos.UpdateUserDto;

import java.util.List;

public class UpdateArtistDto extends UpdateUserDto {
    private List<String> musicalGenres;

    public List<String> getMusicalGenres() {
        return musicalGenres;
    }

    public void setMusicalGenres(List<String> musicalGenres) {
        this.musicalGenres = musicalGenres;
    }
}
