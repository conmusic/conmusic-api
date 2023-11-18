package school.sptech.conmusicapi.modules.artist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
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
import school.sptech.conmusicapi.modules.artist.specifications.ArtistSpecificationsBuilder;
import school.sptech.conmusicapi.modules.genre.entities.Genre;
import school.sptech.conmusicapi.modules.genre.repository.IGenreRepository;
import school.sptech.conmusicapi.modules.media.entities.Media;
import school.sptech.conmusicapi.modules.media.repositories.IMediaRepository;
import school.sptech.conmusicapi.modules.media.services.StorageService;
import school.sptech.conmusicapi.modules.show.repositories.IShowRecordRepository;
import school.sptech.conmusicapi.modules.user.dtos.UserDetailsDto;
import school.sptech.conmusicapi.modules.user.entities.User;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;
import school.sptech.conmusicapi.shared.exceptions.UserForbiddenActionException;
import school.sptech.conmusicapi.shared.utils.collections.GenericObjectList;
import school.sptech.conmusicapi.shared.utils.collections.HashTable;
import school.sptech.conmusicapi.shared.utils.statistics.GroupGenresCount;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Autowired
    private IShowRecordRepository showRecordRepository;

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

    public List<ArtistDto> search(String value, Pageable pageable) {
        ArtistSpecificationsBuilder builder = new ArtistSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(value + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3), null, null);
        }

        Specification<Artist> spec = builder.build();

        List<Artist> artists = artistRepository.findAll(spec, pageable).getContent();

        return artists.stream().map(ArtistMapper::toDto).toList();
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

    public List<ByteArrayResource> getFiles(Integer id) {
        List<Media> medias = mediaRepository.findByUserId(id);

        List<ByteArrayResource> files = new ArrayList<>();

        medias.forEach(media -> {
            byte[] data = storageService.downloadFile(media.getUrl());
            ByteArrayResource resource = new ByteArrayResource(data);
            files.add(resource);
        });

        if (files.isEmpty()){
            throw new EntityNotFoundException(String.format("Artist with id %d was not found.", id));
        }

        return files;
    }

    public String deleteFile(String fileName) {
        return storageService.deleteFile(fileName);
    }

    public List<GroupGenresCount> getMostPopularGenresChartByUserId(Integer lastDays) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDto details = (UserDetailsDto) authentication.getPrincipal();
        User user = userRepository.findByEmail(details.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with email %s was not found", details.getUsername())
                ));

        LocalDate today = LocalDate.now();

        List<GroupGenresCount> genresCountList = showRecordRepository
                .findTopGenresFromDateBetweenInterval(
                        today.minusDays(lastDays).atStartOfDay(),
                        today.atTime(23, 59, 59),
                        user.getId());

        if (genresCountList.isEmpty()) {
            return Collections.emptyList();
        }

        Comparator<GroupGenresCount> comparator = Comparator.comparingLong(GroupGenresCount::getCount).reversed();

        return genresCountList.stream()
                .sorted(comparator)
                .limit(5)
                .toList();
    }
}