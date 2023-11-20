package school.sptech.conmusicapi.modules.artist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.conmusicapi.modules.artist.dtos.ArtistDto;
import school.sptech.conmusicapi.modules.artist.dtos.CreateArtistDto;
import school.sptech.conmusicapi.modules.artist.dtos.UpdateArtistDto;
import school.sptech.conmusicapi.modules.artist.entities.Artist;
import school.sptech.conmusicapi.modules.artist.mapper.ArtistMapper;
import school.sptech.conmusicapi.modules.artist.repositories.IArtistRepository;
import school.sptech.conmusicapi.modules.genre.entities.Genre;
import school.sptech.conmusicapi.modules.genre.repository.IGenreRepository;
import school.sptech.conmusicapi.modules.media.dtos.MediaArtistDto;
import school.sptech.conmusicapi.modules.media.entities.Media;
import school.sptech.conmusicapi.modules.media.mapper.MediaMapper;
import school.sptech.conmusicapi.modules.media.repositories.IMediaRepository;
import school.sptech.conmusicapi.modules.media.services.StorageService;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;
import school.sptech.conmusicapi.shared.exceptions.UserForbiddenActionException;
import school.sptech.conmusicapi.shared.utils.collections.GenericObjectList;
import school.sptech.conmusicapi.shared.utils.collections.HashTable;

import java.io.IOException;
import java.util.ArrayList;
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

    @Autowired
    private StorageService storageService;

    @Autowired
    private IMediaRepository mediaRepository;

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
        Artist artistOpt = artistRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Artist with id %d was not found.", id))
        );

        userRepository.findById(id).filter(artist ->
           artist.getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())
        ).orElseThrow(
                () -> new UserForbiddenActionException("User does not have permission to update this user.")
        );

        Boolean isEmailAlreadyInUse = userRepository.existsByEmail(dto.getEmail());

        if (isEmailAlreadyInUse && !artistOpt.getEmail().equals(dto.getEmail())) {
            throw new BusinessRuleException("Email is already in use.");
        }

        Boolean isCpfAlreadyInUse = userRepository.existsByCpf(dto.getCpf());

        if (isCpfAlreadyInUse && !artistOpt.getCpf().equals(dto.getCpf())) {
            throw new BusinessRuleException("CPF is already in use.");
        }

        Artist updatedArtist = ArtistMapper.fromDtoUpdate(dto, artistOpt);
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
                    auxOrdered.getByIndex(j)
                            .getBirthDate().isAfter(
                                auxOrdered.getByIndex(oldestIndex).getBirthDate()
                            )
                ) {
                    oldestIndex = j;
                }
            }
            auxOrdered.swap(i, oldestIndex);
        }
        
        return auxOrdered.asList();
    }

    public ArtistDto getByArtistId(Integer id) {
        List<Artist> artistList = artistRepository.findAll();

        HashTable hashTable = new HashTable(artistList.size());
        hashTable.insertList(artistList, 0);

        Artist artist = hashTable.search(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Artist with id %d was not found.", id))
        );

        return ArtistMapper.toDto(artist);
    }

    public ArtistDto registerGenreArtist(Integer id, String nameGenre){

        userRepository.findById(id).filter(artist ->
                artist.getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())
        ).orElseThrow(
                () -> new UserForbiddenActionException("The user does not have permission to register genre for this user.")
        );

        Genre genre = genreRepository.findByNameIgnoreCase(nameGenre).orElseThrow(
                () -> new EntityNotFoundException(String.format("Gender with name %s was not found", nameGenre))
        );

        Artist artist = artistRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Artist with id %d was not found.", id))
        );

        artist.addGenders(genre);

        return ArtistMapper.toDto(artistRepository.save(artist));
    }

    public int deleteGenreArtist(Integer id, String nameGenre){

//        FIQUEI EM DÚVIDA SE ISSO VAI NO EM DELETE - ACREDITO QUE DEVERIA
//        userRepository.findById(id).filter(artist ->
//                artist.getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())
//        ).orElseThrow(
//                () -> new UserForbiddenActionException("The user does not have permission to register genre for this user.")
//        );

        Genre genre = genreRepository.findByNameIgnoreCase(nameGenre).orElseThrow(
                () -> new EntityNotFoundException(String.format("Gender with name %s was not found", nameGenre))
        );

        Integer genreId = genre.getId();

        return artistRepository.deleteGenreArtist(id, genreId);
    }

    public String uploadFile(MultipartFile file, int id) throws IOException {

        Artist artist = artistRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Artist with id %d was not found.", id))
        );

        String fileName = storageService.uploadFileArtist(file, artist);

        return fileName;
    }

    public byte[] getFiles(Integer imageId) {

        Media media = mediaRepository.findById(imageId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Image with %d not found.", imageId))
        );

        return storageService.downloadFile(media.getUrl());
    }

    public String deleteFile(String fileName) {
        return storageService.deleteFile(fileName);
    }

    public MediaArtistDto getPerfilImage(Integer id) {

        Media media = mediaRepository.findByUserId(id).stream().findFirst().orElseThrow(
                () -> new EntityNotFoundException(String.format("Artist with id %d was not found.", id))
        );

        return MediaMapper.mapToDto(media);
    }

    public List<MediaArtistDto> getImages(Integer id) {

        List<Media> medias = mediaRepository.findByUserId(id);

        if (medias.isEmpty()){
            throw new EntityNotFoundException(String.format("Artist with id %d was not found.", id));
        }

        return medias.stream()
                .map(MediaMapper::mapToDto)
                .toList();
    }
}