package school.sptech.conmusicapi.modules.genre.mapper;

import school.sptech.conmusicapi.modules.genre.dto.DisplayingGenreDto;
import school.sptech.conmusicapi.modules.genre.dto.RegisterGenreDto;
import school.sptech.conmusicapi.modules.genre.entities.Genre;

public class GenreMapper {

    public static Genre fromDto(RegisterGenreDto registerGenreDto){

        Genre genre = new Genre();

        genre.setName(registerGenreDto.getName());

        return genre;
    }

    public static DisplayingGenreDto toDto(Genre genre){
        DisplayingGenreDto genderDto = new DisplayingGenreDto();

        genderDto.setName(genre.getName());

        return genderDto;
    }
}
