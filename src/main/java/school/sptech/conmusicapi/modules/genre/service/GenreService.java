package school.sptech.conmusicapi.modules.genre.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.genre.dto.DisplayingGenreDto;
import school.sptech.conmusicapi.modules.genre.dto.RegisterGenreDto;
import school.sptech.conmusicapi.modules.genre.entities.Genre;
import school.sptech.conmusicapi.modules.genre.mapper.GenreMapper;
import school.sptech.conmusicapi.modules.genre.repository.IGenreRepository;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    IGenreRepository genderRepository;

    public DisplayingGenreDto register(RegisterGenreDto genderDto){

        Genre genre = GenreMapper.fromDto(genderDto);

        return GenreMapper.toDto(genderRepository.save(genre));
    }

    public List<DisplayingGenreDto> list() {
        return genderRepository
                .findAll()
                .stream()
                .map(GenreMapper::toDto)
                .toList();
    }
}
