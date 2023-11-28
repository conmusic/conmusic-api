package school.sptech.conmusicapi.modules.artist.dtos;

import school.sptech.conmusicapi.modules.user.dtos.UpdateUserDto;

import java.util.List;

public class UpdateArtistDto extends UpdateUserDto {
    private List<Integer> musicalGenres;

    public List<Integer> getMusicalGenres() {
        return musicalGenres;
    }

    public void setMusicalGenres(List<Integer> musicalGenres) {
        this.musicalGenres = musicalGenres;
    }
}
