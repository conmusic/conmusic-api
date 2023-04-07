package school.sptech.conmusicapi.modules.artist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.artist.dtos.ArtistDto;
import school.sptech.conmusicapi.modules.artist.dtos.CreateArtistDto;
import school.sptech.conmusicapi.modules.artist.entities.Artist;
import school.sptech.conmusicapi.modules.artist.mapper.ArtistMapper;
import school.sptech.conmusicapi.modules.artist.repositories.IArtistRepository;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;

import java.util.Optional;

@Service
public class ArtistService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IArtistRepository artistRepository;

    public Optional<ArtistDto> create(CreateArtistDto dto) {
        Boolean isEmailAlreadyInUse = userRepository.existsByEmail(dto.getEmail());

        if (isEmailAlreadyInUse) {
            return Optional.empty();
        }

        Boolean isCpfAlreadyInUse = artistRepository.existsByCpf(dto.getCpf());

        if (isCpfAlreadyInUse) {
            return Optional.empty();
        }

        Artist createdArtist = artistRepository.save(ArtistMapper.fromDto(dto));
        return Optional.of(ArtistMapper.toDto(createdArtist));
    }
}
