package school.sptech.conmusicapi.modules.artist.dtos;

import school.sptech.conmusicapi.modules.genre.entities.Genre;
import school.sptech.conmusicapi.modules.user.dtos.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public class ArtistDto extends UserDto {

    private List<Genre> musicalGenres;

    public List<String> getMusicalGenres() {
        return musicalGenres.stream()
                .map(Genre::getName)
                .collect(Collectors.toList());
    }

    public void setMusicalGenres(List<Genre> musicalGenres) {
        this.musicalGenres = musicalGenres;
    }
}