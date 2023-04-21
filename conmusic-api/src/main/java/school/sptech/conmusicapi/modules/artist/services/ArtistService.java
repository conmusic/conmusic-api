package school.sptech.conmusicapi.modules.artist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import school.sptech.conmusicapi.modules.artist.dtos.ArtistDto;
import school.sptech.conmusicapi.modules.artist.dtos.CreateArtistDto;
import school.sptech.conmusicapi.modules.artist.dtos.UpdateArtistDto;
import school.sptech.conmusicapi.modules.artist.entities.Artist;
import school.sptech.conmusicapi.modules.artist.mapper.ArtistMapper;
import school.sptech.conmusicapi.modules.artist.repositories.IArtistRepository;
import school.sptech.conmusicapi.modules.user.entities.User;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtistService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IArtistRepository artistRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<ArtistDto> create(CreateArtistDto dto) {
        Boolean isEmailAlreadyInUse = userRepository.existsByEmail(dto.getEmail());

        if (isEmailAlreadyInUse) {
            return Optional.empty();
        }

        Boolean isCpfAlreadyInUse = artistRepository.existsByCpf(dto.getCpf());

        if (isCpfAlreadyInUse) {
            return Optional.empty();
        }

        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(hashedPassword);

        Artist createdArtist = artistRepository.save(ArtistMapper.fromDto(dto));
        return Optional.of(ArtistMapper.toDto(createdArtist));
    }

    public Optional<ArtistDto> updateArtistDto(UpdateArtistDto dto){

        if (userRepository.existsById(dto.getId())){
            Artist artist = artistRepository.findById(dto.getId()).get();
            Artist updatedArtist = ArtistMapper.fromDtoUpdate(dto, artist);

            artistRepository.save(updatedArtist);
            return Optional.of(ArtistMapper.toDto(updatedArtist));
        }

        return Optional.empty();
    }

    public List<ArtistDto> findAll() {
        List<Artist> artists = artistRepository.findAll();
        return artists.stream()
                .map(ArtistMapper::toDto)
                .collect(Collectors.toList());
    }
}
