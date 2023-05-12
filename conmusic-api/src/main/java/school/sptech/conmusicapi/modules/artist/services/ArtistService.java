package school.sptech.conmusicapi.modules.artist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.artist.dtos.ArtistDto;
import school.sptech.conmusicapi.modules.artist.dtos.CreateArtistDto;
import school.sptech.conmusicapi.modules.artist.dtos.UpdateArtistDto;
import school.sptech.conmusicapi.modules.artist.entities.Artist;
import school.sptech.conmusicapi.modules.artist.mapper.ArtistMapper;
import school.sptech.conmusicapi.modules.artist.repositories.IArtistRepository;
import school.sptech.conmusicapi.modules.genre.entities.Genre;
import school.sptech.conmusicapi.modules.genre.repository.IGenreRepository;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;
import school.sptech.conmusicapi.shared.utils.GenericObjectList;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IArtistRepository artistRepository;

    @Autowired
    private IGenreRepository genreRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ArtistDto create(CreateArtistDto dto) {
        Boolean isEmailAlreadyInUse = userRepository.existsByEmail(dto.getEmail());

        if (isEmailAlreadyInUse) {
            throw new BusinessRuleException("Email is already in use.");
        }

        Boolean isCpfAlreadyInUse = userRepository.existsByCpf(dto.getCpf());

        if (isCpfAlreadyInUse) {
            throw new BusinessRuleException("CPF is already in use.");
        }

        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(hashedPassword);

        Artist createdArtist = artistRepository.save(ArtistMapper.fromDto(dto));
        return ArtistMapper.toDto(createdArtist);
    }

    public ArtistDto updateArtistDto(UpdateArtistDto dto, Integer id){
        Optional<Artist> artistOpt = artistRepository.findById(id);

        if (artistOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("Artist with id %d was not found.", id));
        }

        Boolean isEmailAlreadyInUse = userRepository.existsByEmail(dto.getEmail());

        if (isEmailAlreadyInUse && !artistOpt.get().getEmail().equals(dto.getEmail())) {
            throw new BusinessRuleException("Email is already in use.");
        }

        Boolean isCpfAlreadyInUse = userRepository.existsByCpf(dto.getCpf());

        if (isCpfAlreadyInUse && !artistOpt.get().getCpf().equals(dto.getCpf())) {
            throw new BusinessRuleException("CPF is already in use.");
        }

        Artist updatedArtist = ArtistMapper.fromDtoUpdate(dto, artistOpt.get());
        updatedArtist.setId(id);
        artistRepository.save(updatedArtist);
        return ArtistMapper.toDto(updatedArtist);
    }

    public List<ArtistDto> findAll() {
        List<Artist> artists = artistRepository.findAll();
        GenericObjectList<ArtistDto> auxOrdered = new GenericObjectList<>(artists.size());

        for (int i = 0; i < artists.size(); i++) {
            auxOrdered.add(ArtistMapper.toDto(artists.get(i)));
        }

        for (int i = 0; i < auxOrdered.getSize() - 1; i++) {
            int oldestIndex = i;

            for (int j = i + 1; j < auxOrdered.getSize(); j++) {
                if (
                    auxOrdered.getElement(j)
                            .getBirthDate().isAfter(
                                auxOrdered.getElement(oldestIndex).getBirthDate()
                            )
                ) {
                    oldestIndex = j;
                }
            }
            auxOrdered.swap(i, oldestIndex);
        }
        
        return auxOrdered.getElements();
    }

    public ArtistDto getByArtistId(Integer id) {
        Optional<Artist> artistOpt = artistRepository.findById(id);

        if (artistOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("Artist with id %d was not found.", id));
        }

        return ArtistMapper.toDto(artistOpt.get());
    }

    public ArtistDto registerGenreArtist(Integer id, String nameGenre){

        Genre genre = genreRepository.findByName(nameGenre).orElseThrow(
                () -> new EntityNotFoundException(String.format("Gender with name %s was not found", nameGenre))
        );

        Artist artist = artistRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Artist with id %d was not found.", id))
        );

        artist.addGenders(genre);

        return ArtistMapper.toDto(artistRepository.save(artist));
    }

    public int deleteGenreArtist(Integer id, String nameGenre){
        Genre genre = genreRepository.findByName(nameGenre).orElseThrow(
                () -> new EntityNotFoundException(String.format("Gender with name %s was not found", nameGenre))
        );

        Integer genreId = genre.getId();

        return artistRepository.deleteGenreArtist(id, genreId);
    }
}
