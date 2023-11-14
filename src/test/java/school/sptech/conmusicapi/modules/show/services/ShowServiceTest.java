package school.sptech.conmusicapi.modules.show.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import school.sptech.conmusicapi.modules.artist.entities.Artist;
import school.sptech.conmusicapi.modules.artist.repositories.IArtistRepository;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.events.repositories.IEventRepository;
import school.sptech.conmusicapi.modules.genre.entities.Genre;
import school.sptech.conmusicapi.modules.manager.entities.Manager;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;
import school.sptech.conmusicapi.modules.schedules.repositories.IScheduleRepository;
import school.sptech.conmusicapi.modules.show.dtos.CreateShowDto;
import school.sptech.conmusicapi.modules.show.dtos.ShowDto;
import school.sptech.conmusicapi.modules.show.dtos.UpdateShowDto;
import school.sptech.conmusicapi.modules.show.entities.Show;
import school.sptech.conmusicapi.modules.show.entities.ShowRecord;
import school.sptech.conmusicapi.modules.show.repositories.IShowRecordRepository;
import school.sptech.conmusicapi.modules.show.repositories.IShowRepository;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;
import school.sptech.conmusicapi.modules.user.dtos.UserDetailsDto;
import school.sptech.conmusicapi.modules.user.entities.User;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ShowServiceTest {
    @Mock
    private IShowRepository showRepository;

    @Mock
    private IArtistRepository artistRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IEventRepository eventRepository;

    @Mock
    private IScheduleRepository scheduleRepository;

    @Mock
    private IShowRecordRepository showRecordRepository;

    @InjectMocks
    private ShowService service;

    // create
    @Test
    @DisplayName("create - Throw exception when artist is not found")
    void createShouldThrowExceptionWhenArtistIsNotFound() {
        // given
        String errorMessage = "Artist with id 1 was not found";

        CreateShowDto dto = new CreateShowDto();
        dto.setArtistId(1);
        dto.setCoverCharge(5.0);
        dto.setValue(200.0);
        dto.setEventId(10);
        dto.setScheduleId(100);

        // when
        Mockito.when(
                showRepository.findByArtistIdAndEventIdAndScheduleId(
                        Mockito.anyInt(),
                        Mockito.anyInt(),
                        Mockito.anyInt())
                )
                .thenReturn(Optional.empty());

        Mockito.when(artistRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        // then
        EntityNotFoundException result = assertThrows(EntityNotFoundException.class, () -> service.create(dto));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(EntityNotFoundException.class, result);
    }

    @Test
    @DisplayName("create - Throw exception when event is not found")
    void createShouldThrowExceptionWhenEventIsNotFound() {
        // given
        String errorMessage = "Event with id 10 was not found";

        Integer artistId = 1;
        Artist artist = new Artist();
        artist.setId(artistId);

        CreateShowDto dto = new CreateShowDto();
        dto.setArtistId(artistId);
        dto.setCoverCharge(5.0);
        dto.setValue(200.0);
        dto.setEventId(10);
        dto.setScheduleId(100);

        // when
        Mockito.when(
                        showRepository.findByArtistIdAndEventIdAndScheduleId(
                                Mockito.anyInt(),
                                Mockito.anyInt(),
                                Mockito.anyInt())
                )
                .thenReturn(Optional.empty());

        Mockito.when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));

        Mockito.when(eventRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        // then
        EntityNotFoundException result = assertThrows(EntityNotFoundException.class, () -> service.create(dto));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(EntityNotFoundException.class, result);
    }

    @Test
    @DisplayName("create - Throw exception when schedule is not found")
    void createShouldThrowExceptionWhenScheduleIsNotFound() {
        // given
        String errorMessage = "Schedule with id 100 was not found";

        Integer artistId = 1;
        Artist artist = new Artist();
        artist.setId(artistId);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);

        CreateShowDto dto = new CreateShowDto();
        dto.setArtistId(artistId);
        dto.setEventId(eventId);
        dto.setCoverCharge(5.0);
        dto.setValue(200.0);
        dto.setScheduleId(100);

        // when
        Mockito.when(
                        showRepository.findByArtistIdAndEventIdAndScheduleId(
                                Mockito.anyInt(),
                                Mockito.anyInt(),
                                Mockito.anyInt())
                )
                .thenReturn(Optional.empty());

        Mockito.when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));

        Mockito.when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        Mockito.when(scheduleRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        // then
        EntityNotFoundException result = assertThrows(EntityNotFoundException.class, () -> service.create(dto));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(EntityNotFoundException.class, result);
    }

    @Test
    @DisplayName("create - Throw exception when user is not found")
    void createShouldThrowExceptionWhenUserIsNotFound() {
        // given
        String errorMessage = "User with email artist1@email.com was not found";

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(artist);

        CreateShowDto dto = new CreateShowDto();
        dto.setArtistId(artistId);
        dto.setEventId(eventId);
        dto.setCoverCharge(5.0);
        dto.setValue(200.0);
        dto.setScheduleId(100);

        // when
        Mockito.when(
                        showRepository.findByArtistIdAndEventIdAndScheduleId(
                                Mockito.anyInt(),
                                Mockito.anyInt(),
                                Mockito.anyInt())
                )
                .thenReturn(Optional.empty());

        Mockito.when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));

        Mockito.when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        Mockito.when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        // then
        EntityNotFoundException result = assertThrows(EntityNotFoundException.class, () -> service.create(dto));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(EntityNotFoundException.class, result);
    }

    @ParameterizedTest
    @ValueSource(strings = { "Artist", "Manager" })
    @DisplayName("create - Throw exception when user is neither the artist nor the manager from the show")
    void createShouldThrowExceptionWhenUserIsNeitherArtistNorManagerFromShow(String userType) {
        // given
        String errorMessage = "The user is neither the artist nor the manager of the show";

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);

        Integer otherArtistId = 3;
        String otherArtistEmail = "artist3@email.com";
        Artist otherArtist = new Artist();
        otherArtist.setId(otherArtistId);
        otherArtist.setEmail(otherArtistEmail);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer otherManagerId = 4;
        String otherManagerEmail = "manager4@email.com";
        Manager otherManager = new Manager();
        otherManager.setId(otherManagerId);
        otherManager.setEmail(otherManagerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        String authenticatedUserEmail = userType.equals("Artist")
                ? otherArtistEmail
                : otherManagerEmail;

        User authenticatedUser = userType.equals("Artist")
                ? otherArtist
                : otherManager;

        UserDetailsDto userDetailsDto = new UserDetailsDto(authenticatedUser);

        CreateShowDto dto = new CreateShowDto();
        dto.setArtistId(artistId);
        dto.setEventId(eventId);
        dto.setCoverCharge(5.0);
        dto.setValue(200.0);
        dto.setScheduleId(100);

        // when
        Mockito.when(
                        showRepository.findByArtistIdAndEventIdAndScheduleId(
                                Mockito.anyInt(),
                                Mockito.anyInt(),
                                Mockito.anyInt())
                )
                .thenReturn(Optional.empty());

        Mockito.when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));

        Mockito.when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        Mockito.when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(authenticatedUserEmail)).thenReturn(Optional.of(authenticatedUser));

        // then
        BusinessRuleException result = assertThrows(BusinessRuleException.class, () -> service.create(dto));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(BusinessRuleException.class, result);
    }

    @Test
    @DisplayName("create - Throw exception when schedule has already been confirmed")
    void createShouldThrowExceptionWhenScheduleIsNotFromTheEvent() {
        // given
        String errorMessage = "Schedule is not from the event that was informed";

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event1 = new Event();
        event1.setId(eventId);
        event1.setEstablishment(establishment);

        Event event2 = new Event();
        event2.setId(11);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event2);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(artist);

        CreateShowDto dto = new CreateShowDto();
        dto.setArtistId(artistId);
        dto.setEventId(eventId);
        dto.setCoverCharge(5.0);
        dto.setValue(200.0);
        dto.setScheduleId(100);

        // when
        Mockito.when(
                        showRepository.findByArtistIdAndEventIdAndScheduleId(
                                Mockito.anyInt(),
                                Mockito.anyInt(),
                                Mockito.anyInt())
                )
                .thenReturn(Optional.empty());

        Mockito.when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));

        Mockito.when(eventRepository.findById(eventId)).thenReturn(Optional.of(event1));

        Mockito.when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(artistEmail)).thenReturn(Optional.of(artist));

        // then
        BusinessRuleException result = assertThrows(BusinessRuleException.class, () -> service.create(dto));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(BusinessRuleException.class, result);
    }

    @Test
    @DisplayName("create - Throw exception when schedule has already started")
    void createShouldThrowExceptionWhenScheduleHasAlreadyStarted() {
        // given
        String errorMessage = "The date of this show has already happened.";

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().minusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(artist);

        CreateShowDto dto = new CreateShowDto();
        dto.setArtistId(artistId);
        dto.setEventId(eventId);
        dto.setCoverCharge(5.0);
        dto.setValue(200.0);
        dto.setScheduleId(100);

        // when
        Mockito.when(
                        showRepository.findByArtistIdAndEventIdAndScheduleId(
                                Mockito.anyInt(),
                                Mockito.anyInt(),
                                Mockito.anyInt())
                )
                .thenReturn(Optional.empty());

        Mockito.when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));

        Mockito.when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        Mockito.when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(artistEmail)).thenReturn(Optional.of(artist));

        // then
        BusinessRuleException result = assertThrows(BusinessRuleException.class, () -> service.create(dto));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(BusinessRuleException.class, result);
    }

    @Test
    @DisplayName("create - Throw exception when schedule has already been confirmed")
    void createShouldThrowExceptionWhenScheduleHasAlreadyBeenConfirmed() {
        // given
        String errorMessage = "Schedule is already confirmed";

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(true);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(artist);

        CreateShowDto dto = new CreateShowDto();
        dto.setArtistId(artistId);
        dto.setEventId(eventId);
        dto.setCoverCharge(5.0);
        dto.setValue(200.0);
        dto.setScheduleId(100);

        // when
        Mockito.when(
                        showRepository.findByArtistIdAndEventIdAndScheduleId(
                                Mockito.anyInt(),
                                Mockito.anyInt(),
                                Mockito.anyInt())
                )
                .thenReturn(Optional.empty());

        Mockito.when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));

        Mockito.when(eventRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(event));

        Mockito.when(scheduleRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(schedule));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(artistEmail)).thenReturn(Optional.of(artist));

        // then
        BusinessRuleException result = assertThrows(BusinessRuleException.class, () -> service.create(dto));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(BusinessRuleException.class, result);
    }

    @Test
    @DisplayName("create - Return show with ARTIST_PROPOSAL status when requesting user is an artist")
    void createShowWithArtistProposalStatusWhenUserIsArtist() {
        // given
        Integer expectedId = 1000;
        Double expectedCoverCharge = 5.0;
        Double expectedValue = 200.0;
        ShowStatusEnum expectedStatus = ShowStatusEnum.ARTIST_PROPOSAL;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(artist);

        CreateShowDto dto = new CreateShowDto();
        dto.setArtistId(artistId);
        dto.setEventId(eventId);
        dto.setCoverCharge(expectedCoverCharge);
        dto.setValue(expectedValue);
        dto.setScheduleId(scheduleId);

        // when
        Mockito.when(
                        showRepository.findByArtistIdAndEventIdAndScheduleId(
                                Mockito.anyInt(),
                                Mockito.anyInt(),
                                Mockito.anyInt())
                )
                .thenReturn(Optional.empty());

        Mockito.when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));

        Mockito.when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        Mockito.when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(artistEmail)).thenReturn(Optional.of(artist));

        Mockito.when(showRepository.save(Mockito.any(Show.class))).thenAnswer(i -> {
            Show show = (Show) i.getArguments()[0];
            show.setId(expectedId);
            return show;
        });

        // then
        ShowDto showDto = service.create(dto);

        // assert
        assertEquals(expectedId, showDto.getId());
        assertEquals(expectedValue, showDto.getValue());
        assertEquals(expectedCoverCharge, showDto.getCoverCharge());
        assertEquals(artistId, showDto.getArtist().getId());
        assertEquals(scheduleId, showDto.getSchedule().getId());
        assertEquals(eventId, showDto.getEvent().getId());
        assertEquals(expectedStatus, showDto.getStatus());
    }

    @Test
    @DisplayName("create - Return show with MANAGER_PROPOSAL status when requesting user is an artist")
    void createShowWithManagerProposalStatusWhenUserIsManager() {
        // given
        Integer expectedId = 1000;
        Double expectedCoverCharge = 5.0;
        Double expectedValue = 200.0;
        ShowStatusEnum expectedStatus = ShowStatusEnum.MANAGER_PROPOSAL;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(manager);

        CreateShowDto dto = new CreateShowDto();
        dto.setArtistId(artistId);
        dto.setEventId(eventId);
        dto.setCoverCharge(expectedCoverCharge);
        dto.setValue(expectedValue);
        dto.setScheduleId(scheduleId);

        // when
        Mockito.when(
                        showRepository.findByArtistIdAndEventIdAndScheduleId(
                                Mockito.anyInt(),
                                Mockito.anyInt(),
                                Mockito.anyInt())
                )
                .thenReturn(Optional.empty());

        Mockito.when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));

        Mockito.when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        Mockito.when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(managerEmail)).thenReturn(Optional.of(manager));

        Mockito.when(showRepository.save(Mockito.any(Show.class))).thenAnswer(i -> {
            Show show = (Show) i.getArguments()[0];
            show.setId(expectedId);
            return show;
        });

        // then
        ShowDto showDto = service.create(dto);

        // assert
        assertEquals(expectedId, showDto.getId());
        assertEquals(expectedValue, showDto.getValue());
        assertEquals(expectedCoverCharge, showDto.getCoverCharge());
        assertEquals(artistId, showDto.getArtist().getId());
        assertEquals(scheduleId, showDto.getSchedule().getId());
        assertEquals(eventId, showDto.getEvent().getId());
        assertEquals(expectedStatus, showDto.getStatus());
    }

    // update
    @Test
    @DisplayName("update - Throw exception when show is not found")
    void updateShouldThrowExceptionWhenShowIsNotFound() {
        // given
        String errorMessage = "Show with id 1000 was not found";

        Integer showId = 1000;
        Double expectedValue = 500.0;
        Double expectedCoverCharge = 5.0;

        UpdateShowDto dto = new UpdateShowDto();
        dto.setValue(expectedValue);
        dto.setCoverCharge(expectedCoverCharge);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.empty());

        // then
        EntityNotFoundException result = assertThrows(EntityNotFoundException.class, () -> service.update(showId, dto));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(EntityNotFoundException.class, result);
    }

    @Test
    @DisplayName("update - Throw exception when user is not found")
    void updateShouldThrowExceptionWhenUserIsNotFound() {
        // given
        String errorMessage = "User with email artist1@email.com was not found";

        Integer showId = 1000;
        Double expectedValue = 500.0;
        Double expectedCoverCharge = 5.0;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(artist);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(ShowStatusEnum.ARTIST_PROPOSAL);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(100.0);
        show.setCoverCharge(5.0);

        UpdateShowDto dto = new UpdateShowDto();
        dto.setValue(expectedValue);
        dto.setCoverCharge(expectedCoverCharge);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(artistEmail)).thenReturn(Optional.empty());

        // then
        EntityNotFoundException result = assertThrows(EntityNotFoundException.class, () -> service.update(showId, dto));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(EntityNotFoundException.class, result);
    }

    @ParameterizedTest
    @ValueSource(strings = { "Artist", "Manager" })
    @DisplayName("update - Throw exception when user is neither the artist nor the manager from the show")
    void updateShouldThrowExceptionWhenUserIsNeitherArtistNorManagerFromShow(String userType) {
        // given
        String errorMessage = "Unrelated users cannot request for changes";

        Integer showId = 1000;
        Double expectedValue = 500.0;
        Double expectedCoverCharge = 5.0;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer otherArtistId = 3;
        String otherArtistEmail = "artist3@email.com";
        Artist otherArtist = new Artist();
        otherArtist.setId(otherArtistId);
        otherArtist.setEmail(otherArtistEmail);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer otherManagerId = 4;
        String otherManagerEmail = "manager4@email.com";
        Manager otherManager = new Manager();
        otherManager.setId(otherManagerId);
        otherManager.setEmail(otherManagerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        String authenticatedUserEmail = userType.equals("Artist")
                ? otherArtistEmail
                : otherManagerEmail;

        User authenticatedUser = userType.equals("Artist")
                ? otherArtist
                : otherManager;

        UserDetailsDto userDetailsDto = new UserDetailsDto(authenticatedUser);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(ShowStatusEnum.ARTIST_PROPOSAL);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(100.0);
        show.setCoverCharge(5.0);

        UpdateShowDto dto = new UpdateShowDto();
        dto.setValue(expectedValue);
        dto.setCoverCharge(expectedCoverCharge);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(authenticatedUserEmail)).thenReturn(Optional.of(authenticatedUser));

        // then
        BusinessRuleException result = assertThrows(BusinessRuleException.class, () -> service.update(showId, dto));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(BusinessRuleException.class, result);
    }

    @ParameterizedTest
    @EnumSource(value = ShowStatusEnum.class, names = { "CONFIRMED", "CONCLUDED" })
    @DisplayName("update - Throw exception when updating a show that is confirmed or concluded")
    void updateShouldThrowExceptionWhenScheduleHasAlreadyHappened(ShowStatusEnum showStatusEnum) {
        // given
        String errorMessage = "The details of this show are already agreed between the parties";

        Integer showId = 1000;
        Double expectedValue = 500.0;
        Double expectedCoverCharge = 5.0;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(artist);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(showStatusEnum);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(100.0);
        show.setCoverCharge(5.0);

        UpdateShowDto dto = new UpdateShowDto();
        dto.setValue(expectedValue);
        dto.setCoverCharge(expectedCoverCharge);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(artistEmail)).thenReturn(Optional.of(artist));

        // then
        BusinessRuleException result = assertThrows(BusinessRuleException.class, () -> service.update(showId, dto));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(BusinessRuleException.class, result);
    }

    @ParameterizedTest
    @EnumSource(
            value = ShowStatusEnum.class,
            names = { "NEGOTIATION", "ARTIST_ACCEPTED", "MANAGER_ACCEPTED", "CONFIRMED", "CONCLUDED" },
            mode = EnumSource.Mode.EXCLUDE)
    @DisplayName("update - Throw exception when updating a show when status forbids changes")
    void updateShouldThrowExceptionWhenScheduleStatusDoesNotAllowChanges(ShowStatusEnum showStatusEnum) {
        // given
        String errorMessage = "It's not possible to negotiate the details of this show";

        Integer showId = 1000;
        Double expectedValue = 500.0;
        Double expectedCoverCharge = 5.0;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(artist);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(showStatusEnum);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(100.0);
        show.setCoverCharge(5.0);

        UpdateShowDto dto = new UpdateShowDto();
        dto.setValue(expectedValue);
        dto.setCoverCharge(expectedCoverCharge);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(artistEmail)).thenReturn(Optional.of(artist));

        // then
        BusinessRuleException result = assertThrows(BusinessRuleException.class, () -> service.update(showId, dto));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(BusinessRuleException.class, result);
    }

    @ParameterizedTest
    @EnumSource(value = ShowStatusEnum.class, names = { "NEGOTIATION", "ARTIST_ACCEPTED", "MANAGER_ACCEPTED" })
    @DisplayName("update - Return updated show and insert record in history")
    void updateShouldReturnUpdatedShowAndInsertNewRecordInHistory(ShowStatusEnum showStatusEnum) {
        // given
        int expectedRecordSaves = 1;

        Integer showId = 1000;
        Double expectedValue = 500.0;
        Double expectedCoverCharge = 10.0;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(artist);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(showStatusEnum);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(100.0);
        show.setCoverCharge(5.0);

        UpdateShowDto dto = new UpdateShowDto();
        dto.setValue(expectedValue);
        dto.setCoverCharge(expectedCoverCharge);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(artistEmail)).thenReturn(Optional.of(artist));

        Mockito.when(showRecordRepository.save(Mockito.any(ShowRecord.class))).thenAnswer(i -> i.getArguments()[0]);

        Mockito.when(showRepository.save(Mockito.any(Show.class))).thenAnswer(i -> i.getArguments()[0]);

        // then
        ShowDto result = service.update(showId, dto);

        // assert
        assertEquals(showId, result.getId());
        assertEquals(ShowStatusEnum.NEGOTIATION, result.getStatus());
        assertEquals(expectedCoverCharge, result.getCoverCharge());
        assertEquals(expectedValue, result.getValue());
        assertEquals(eventId, result.getEvent().getId());
        assertEquals(artistId, result.getArtist().getId());
        assertEquals(scheduleId, result.getSchedule().getId());
        Mockito.verify(showRecordRepository, Mockito.times(expectedRecordSaves)).save(Mockito.any(ShowRecord.class));
    }

    // acceptProposal
    @Test
    @DisplayName("acceptProposal - Throw exception when show is not found")
    void acceptProposalShouldThrowExceptionWhenShowIsNotFound() {
        // given
        String errorMessage = "Show with id 1000 was not found";
        Integer showId = 1000;

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.empty());

        // then
        EntityNotFoundException result = assertThrows(EntityNotFoundException.class, () -> service.acceptProposal(showId));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(EntityNotFoundException.class, result);
    }

    @Test
    @DisplayName("acceptProposal - Throw exception when user is not found")
    void acceptProposalShouldThrowExceptionWhenUserIsNotFound() {
        // given
        String errorMessage = "User with email artist1@email.com was not found";

        Integer showId = 1000;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(artist);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(ShowStatusEnum.ARTIST_PROPOSAL);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(100.0);
        show.setCoverCharge(5.0);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(artistEmail)).thenReturn(Optional.empty());

        // then
        EntityNotFoundException result = assertThrows(EntityNotFoundException.class, () -> service.acceptProposal(showId));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(EntityNotFoundException.class, result);
    }

    @Test
    @DisplayName("acceptProposal - Throw exception when user is not the artist from show")
    void acceptProposalShouldThrowExceptionWhenUserIsNotArtistFromShow() {
        // given
        String errorMessage = "Unrelated users cannot request for changes";

        Integer showId = 1000;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer otherArtistId = 3;
        String otherArtistEmail = "artist3@email.com";
        Artist otherArtist = new Artist();
        otherArtist.setId(otherArtistId);
        otherArtist.setEmail(otherArtistEmail);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(otherArtist);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(ShowStatusEnum.ARTIST_PROPOSAL);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(100.0);
        show.setCoverCharge(5.0);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(otherArtistEmail)).thenReturn(Optional.of(otherArtist));

        // then
        BusinessRuleException result = assertThrows(BusinessRuleException.class, () -> service.acceptProposal(showId));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(BusinessRuleException.class, result);
    }

    @Test
    @DisplayName("acceptProposal - Throw exception when user is not the manager from show")
    void acceptProposalShouldThrowExceptionWhenUserIsNotManagerFromShow() {
        // given
        String errorMessage = "Unrelated users cannot request for changes";

        Integer showId = 1000;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer otherManagerId = 3;
        String otherManagerEmail = "manager3@email.com";
        Manager otherManager = new Manager();
        otherManager.setId(otherManagerId);
        otherManager.setEmail(otherManagerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(otherManager);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(ShowStatusEnum.ARTIST_PROPOSAL);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(100.0);
        show.setCoverCharge(5.0);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(otherManagerEmail)).thenReturn(Optional.of(otherManager));

        // then
        BusinessRuleException result = assertThrows(BusinessRuleException.class, () -> service.acceptProposal(showId));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(BusinessRuleException.class, result);
    }

    @Test
    @DisplayName("acceptProposal - Throw exception when show is already in negotiation")
    void acceptProposalShouldThrowExceptionWhenShowIsAlreadyInNegotiation() {
        // given
        String errorMessage = "Show is already in negotiation";

        Integer showId = 1000;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(artist);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(ShowStatusEnum.NEGOTIATION);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(100.0);
        show.setCoverCharge(5.0);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(artistEmail)).thenReturn(Optional.of(artist));

        // then
        BusinessRuleException result = assertThrows(BusinessRuleException.class, () -> service.acceptProposal(showId));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(BusinessRuleException.class, result);
    }

    @ParameterizedTest
    @EnumSource(
            value = ShowStatusEnum.class,
            names = { "NEGOTIATION", "ARTIST_PROPOSAL", "MANAGER_PROPOSAL", "ARTIST_ACCEPTED", "MANAGER_ACCEPTED" },
            mode = EnumSource.Mode.EXCLUDE)
    @DisplayName("acceptProposal - Throw exception when show status forbids status change")
    void acceptProposalShouldThrowExceptionWhenShowStatusForbidsStatusChange(ShowStatusEnum showStatusEnum) {
        // given
        String errorMessage = String.format("It is forbidden to change status from %s to NEGOTIATION", showStatusEnum);

        Integer showId = 1000;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(artist);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(showStatusEnum);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(100.0);
        show.setCoverCharge(5.0);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(artistEmail)).thenReturn(Optional.of(artist));

        // then
        BusinessRuleException result = assertThrows(BusinessRuleException.class, () -> service.acceptProposal(showId));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(BusinessRuleException.class, result);
    }

    @ParameterizedTest
    @EnumSource(value = ShowStatusEnum.class, names = { "ARTIST_PROPOSAL", "MANAGER_PROPOSAL" })
    @DisplayName("acceptProposal - Throw exception when user who made the proposal tries to accept it")
    void acceptProposalShouldThrowExceptionWhenUserWhoMadeTheProposalTriesToAcceptIt(ShowStatusEnum showStatusEnum) {
        // given
        String errorMessage = "The user who made the proposal cannot accept it";

        Integer showId = 1000;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        String authenticatedUserEmail = showStatusEnum.equals(ShowStatusEnum.ARTIST_PROPOSAL)
                ? artistEmail
                : managerEmail;

        User authenticatedUser = showStatusEnum.equals(ShowStatusEnum.ARTIST_PROPOSAL)
                ? artist
                : manager;

        UserDetailsDto userDetailsDto = new UserDetailsDto(authenticatedUser);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(showStatusEnum);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(100.0);
        show.setCoverCharge(5.0);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(authenticatedUserEmail)).thenReturn(Optional.of(authenticatedUser));

        // then
        BusinessRuleException result = assertThrows(BusinessRuleException.class, () -> service.acceptProposal(showId));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(BusinessRuleException.class, result);
    }

    @ParameterizedTest
    @EnumSource(value = ShowStatusEnum.class, names = { "ARTIST_PROPOSAL", "MANAGER_PROPOSAL" })
    @DisplayName("acceptProposal - Return Record and update Proposal to Reject when Schedule has already happened")
    void acceptProposalShouldSaveRecordAndUpdateProposalToRejectedWhenScheduleHasAlreadyHappened(ShowStatusEnum showStatusEnum) {
        // given
        int expectedRecordSaves = 1;
        String errorMessage = "The proposal date has passed";
        Integer showId = 1000;
        Double expectedValue = 100.0;
        Double expectedCoverCharge = 5.0;
        ShowStatusEnum expectedStatus = showStatusEnum.equals(ShowStatusEnum.ARTIST_PROPOSAL)
                ? ShowStatusEnum.MANAGER_REJECTED
                : ShowStatusEnum.ARTIST_REJECTED;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().minusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        String authenticatedUserEmail = showStatusEnum.equals(ShowStatusEnum.ARTIST_PROPOSAL)
                ? managerEmail
                : artistEmail;

        User authenticatedUser = showStatusEnum.equals(ShowStatusEnum.ARTIST_PROPOSAL)
                ? manager
                : artist;

        UserDetailsDto userDetailsDto = new UserDetailsDto(authenticatedUser);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(showStatusEnum);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(expectedValue);
        show.setCoverCharge(expectedCoverCharge);

        ArgumentCaptor<Show> showRepositorySaveCaptor = ArgumentCaptor.forClass(Show.class);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(authenticatedUserEmail)).thenReturn(Optional.of(authenticatedUser));

        Mockito.when(showRepository.save(Mockito.any(Show.class))).thenAnswer(i -> i.getArguments()[0]);

        // then
        BusinessRuleException error = assertThrows(BusinessRuleException.class, () -> service.acceptProposal(showId));

        // assert
        assertEquals(errorMessage, error.getMessage());
        assertInstanceOf(BusinessRuleException.class, error);
        Mockito.verify(showRecordRepository, Mockito.times(expectedRecordSaves)).save(Mockito.any(ShowRecord.class));
        Mockito.verify(showRepository).save(showRepositorySaveCaptor.capture());
        assertEquals(expectedStatus, showRepositorySaveCaptor.getValue().getStatus());
    }

    @ParameterizedTest
    @EnumSource(value = ShowStatusEnum.class, names = { "ARTIST_PROPOSAL", "MANAGER_PROPOSAL" })
    @DisplayName("acceptProposal - Return Record and update Proposal to Manager Rejected when Schedule has already been confirmed")
    void acceptProposalShouldSaveRecordAndUpdateProposalToManagerRejectedWhenScheduleHasAlreadyBeenConfirmed(ShowStatusEnum showStatusEnum) {
        // given
        int expectedRecordSaves = 1;
        String errorMessage = "The schedule already has one show confirmed";
        Integer showId = 1000;
        Double expectedValue = 100.0;
        Double expectedCoverCharge = 5.0;
        ShowStatusEnum expectedStatus = ShowStatusEnum.MANAGER_REJECTED;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(true);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        String authenticatedUserEmail = showStatusEnum.equals(ShowStatusEnum.ARTIST_PROPOSAL)
                ? managerEmail
                : artistEmail;

        User authenticatedUser = showStatusEnum.equals(ShowStatusEnum.ARTIST_PROPOSAL)
                ? manager
                : artist;

        UserDetailsDto userDetailsDto = new UserDetailsDto(authenticatedUser);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(showStatusEnum);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(expectedValue);
        show.setCoverCharge(expectedCoverCharge);

        ArgumentCaptor<Show> showRepositorySaveCaptor = ArgumentCaptor.forClass(Show.class);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(authenticatedUserEmail)).thenReturn(Optional.of(authenticatedUser));

        Mockito.when(showRepository.save(Mockito.any(Show.class))).thenAnswer(i -> i.getArguments()[0]);

        // then
        BusinessRuleException error = assertThrows(BusinessRuleException.class, () -> service.acceptProposal(showId));

        // assert
        assertEquals(errorMessage, error.getMessage());
        assertInstanceOf(BusinessRuleException.class, error);
        Mockito.verify(showRecordRepository, Mockito.times(expectedRecordSaves)).save(Mockito.any(ShowRecord.class));
        Mockito.verify(showRepository).save(showRepositorySaveCaptor.capture());
        assertEquals(expectedStatus, showRepositorySaveCaptor.getValue().getStatus());
    }

    @ParameterizedTest
    @EnumSource(value = ShowStatusEnum.class, names = { "ARTIST_PROPOSAL", "MANAGER_PROPOSAL" })
    @DisplayName("acceptProposal - Return Record and update Proposal to Negotiation")
    void acceptProposalShouldSaveRecordAndUpdateProposalToNegotiation(ShowStatusEnum showStatusEnum) {
        // given
        int expectedSaves = 1;
        Integer showId = 1000;
        Double expectedValue = 100.0;
        Double expectedCoverCharge = 5.0;
        ShowStatusEnum expectedStatus = ShowStatusEnum.NEGOTIATION;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        String authenticatedUserEmail = showStatusEnum.equals(ShowStatusEnum.ARTIST_PROPOSAL)
                ? managerEmail
                : artistEmail;

        User authenticatedUser = showStatusEnum.equals(ShowStatusEnum.ARTIST_PROPOSAL)
                ? manager
                : artist;

        UserDetailsDto userDetailsDto = new UserDetailsDto(authenticatedUser);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(showStatusEnum);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(expectedValue);
        show.setCoverCharge(expectedCoverCharge);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(authenticatedUserEmail)).thenReturn(Optional.of(authenticatedUser));

        Mockito.when(showRepository.save(Mockito.any(Show.class))).thenAnswer(i -> i.getArguments()[0]);

        // then
        ShowDto result = service.acceptProposal(showId);

        // assert
        Mockito.verify(showRecordRepository, Mockito.times(expectedSaves)).save(Mockito.any(ShowRecord.class));
        Mockito.verify(showRepository, Mockito.times(expectedSaves)).save(Mockito.any(Show.class));
        assertEquals(showId, result.getId());
        assertEquals(expectedValue, result.getValue());
        assertEquals(expectedCoverCharge, result.getCoverCharge());
        assertEquals(expectedStatus, result.getStatus());
        assertEquals(eventId, result.getEvent().getId());
        assertEquals(artistId, result.getArtist().getId());
        assertEquals(scheduleId, result.getSchedule().getId());
    }

    // rejectProposal
    @Test
    @DisplayName("rejectProposal - Throw exception when show is not found")
    void rejectProposalShouldThrowExceptionWhenShowIsNotFound() {
        // given
        String errorMessage = "Show with id 1000 was not found";
        Integer showId = 1000;

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.empty());

        // then
        EntityNotFoundException result = assertThrows(EntityNotFoundException.class, () -> service.rejectProposal(showId));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(EntityNotFoundException.class, result);
    }

    @Test
    @DisplayName("rejectProposal - Throw exception when user is not found")
    void rejectProposalShouldThrowExceptionWhenUserIsNotFound() {
        // given
        String errorMessage = "User with email artist1@email.com was not found";

        Integer showId = 1000;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(artist);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(ShowStatusEnum.ARTIST_PROPOSAL);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(100.0);
        show.setCoverCharge(5.0);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(artistEmail)).thenReturn(Optional.empty());

        // then
        EntityNotFoundException result = assertThrows(EntityNotFoundException.class, () -> service.rejectProposal(showId));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(EntityNotFoundException.class, result);
    }

    @Test
    @DisplayName("rejectProposal - Throw exception when user is not the artist from show")
    void rejectProposalShouldThrowExceptionWhenUserIsNotArtistFromShow() {
        // given
        String errorMessage = "Unrelated users cannot request for changes";

        Integer showId = 1000;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer otherArtistId = 3;
        String otherArtistEmail = "artist3@email.com";
        Artist otherArtist = new Artist();
        otherArtist.setId(otherArtistId);
        otherArtist.setEmail(otherArtistEmail);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(otherArtist);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(ShowStatusEnum.ARTIST_PROPOSAL);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(100.0);
        show.setCoverCharge(5.0);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(otherArtistEmail)).thenReturn(Optional.of(otherArtist));

        // then
        BusinessRuleException result = assertThrows(BusinessRuleException.class, () -> service.rejectProposal(showId));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(BusinessRuleException.class, result);
    }

    @Test
    @DisplayName("rejectProposal - Throw exception when user is not the manager from show")
    void rejectProposalShouldThrowExceptionWhenUserIsNotManagerFromShow() {
        // given
        String errorMessage = "Unrelated users cannot request for changes";

        Integer showId = 1000;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer otherManagerId = 3;
        String otherManagerEmail = "manager3@email.com";
        Manager otherManager = new Manager();
        otherManager.setId(otherManagerId);
        otherManager.setEmail(otherManagerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(otherManager);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(ShowStatusEnum.ARTIST_PROPOSAL);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(100.0);
        show.setCoverCharge(5.0);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(otherManagerEmail)).thenReturn(Optional.of(otherManager));

        // then
        BusinessRuleException result = assertThrows(BusinessRuleException.class, () -> service.acceptProposal(showId));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(BusinessRuleException.class, result);
    }

    @ParameterizedTest
    @EnumSource(
            value = ShowStatusEnum.class,
            names = { "ARTIST_PROPOSAL", "MANAGER_PROPOSAL" },
            mode = EnumSource.Mode.EXCLUDE)
    @DisplayName("rejectProposal - Throw exception when show status forbids status change")
    void rejectProposalShouldThrowExceptionWhenShowStatusForbidsStatusChange(ShowStatusEnum showStatusEnum) {
        // given
        String errorMessage = String.format("It is forbidden to change status from %s to ARTIST_REJECTED", showStatusEnum);

        Integer showId = 1000;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(artist);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(showStatusEnum);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(100.0);
        show.setCoverCharge(5.0);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(artistEmail)).thenReturn(Optional.of(artist));

        // then
        BusinessRuleException result = assertThrows(BusinessRuleException.class, () -> service.rejectProposal(showId));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(BusinessRuleException.class, result);
    }

    @ParameterizedTest
    @EnumSource(value = ShowStatusEnum.class, names = { "ARTIST_PROPOSAL", "MANAGER_PROPOSAL" })
    @DisplayName("rejectProposal - Save record and update show status to Rejected")
    void rejectProposalShouldSaveRecordAndUpdateShowStatusToRejected(ShowStatusEnum showStatusEnum) {
        // given
        int expectedSaves = 1;
        Integer showId = 1000;
        ShowStatusEnum expectedStatus = showStatusEnum.equals(ShowStatusEnum.ARTIST_PROPOSAL)
                ? ShowStatusEnum.MANAGER_REJECTED
                : ShowStatusEnum.ARTIST_REJECTED;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        String authenticatedEmail = showStatusEnum.equals(ShowStatusEnum.ARTIST_PROPOSAL)
                ? managerEmail
                : artistEmail;

        User authenticatedUser = showStatusEnum.equals(ShowStatusEnum.ARTIST_PROPOSAL)
                ? manager
                : artist;

        UserDetailsDto userDetailsDto = new UserDetailsDto(authenticatedUser);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(showStatusEnum);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(100.0);
        show.setCoverCharge(5.0);

        ArgumentCaptor<ShowRecord> showRecordRepositorySaveCaptor = ArgumentCaptor.forClass(ShowRecord.class);
        ArgumentCaptor<Show> showRepositorySaveCaptor = ArgumentCaptor.forClass(Show.class);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(authenticatedEmail)).thenReturn(Optional.of(authenticatedUser));

        Mockito.when(showRepository.save(Mockito.any(Show.class))).thenAnswer(i -> i.getArguments()[0]);

        // then
        service.rejectProposal(showId);

        // assert
        Mockito.verify(showRepository, Mockito.times(expectedSaves)).save(Mockito.any(Show.class));
        Mockito.verify(showRepository).save(showRepositorySaveCaptor.capture());
        assertEquals(expectedStatus, showRepositorySaveCaptor.getValue().getStatus());
    }

    // acceptTermsOfNegotiation
    @Test
    @DisplayName("acceptTermsOfNegotiation - Throw exception when show is not found")
    void acceptTermsOfNegotiationShouldThrowExceptionWhenShowIsNotFound() {
        // given
        String errorMessage = "Show with id 1000 was not found";
        Integer showId = 1000;

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.empty());

        // then
        EntityNotFoundException result = assertThrows(EntityNotFoundException.class, () -> service.acceptTermsOfNegotiation(showId));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(EntityNotFoundException.class, result);
    }

    @Test
    @DisplayName("acceptTermsOfNegotiation - Throw exception when user is not found")
    void acceptTermsOfNegotiationShouldThrowExceptionWhenUserIsNotFound() {
        // given
        String errorMessage = "User with email artist1@email.com was not found";

        Integer showId = 1000;

        Genre genre = new Genre();
        genre.setId(9);
        genre.setName("Genre name");

        Integer artistId = 1;
        String artistEmail = "artist1@email.com";
        Artist artist = new Artist();
        artist.setId(artistId);
        artist.setEmail(artistEmail);
        artist.addGenders(genre);

        Integer managerId = 2;
        String managerEmail = "manager2@email.com";
        Manager manager = new Manager();
        manager.setId(managerId);
        manager.setEmail(managerEmail);

        Integer establishmentId = 5;
        Establishment establishment = new Establishment();
        establishment.setId(establishmentId);
        establishment.setManager(manager);

        Integer eventId = 10;
        Event event = new Event();
        event.setId(eventId);
        event.setEstablishment(establishment);
        event.setGenre(genre);

        Integer scheduleId = 100;
        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setConfirmed(false);
        schedule.setEvent(event);
        schedule.setStartDateTime(LocalDateTime.now().plusMinutes(60));
        schedule.setEndDateTime(LocalDateTime.now().plusMinutes(120));

        Authentication authenticationMock = Mockito.mock(Authentication.class);
        SecurityContext securityContextMock = Mockito.mock(SecurityContext.class);

        UserDetailsDto userDetailsDto = new UserDetailsDto(artist);

        Show show = new Show();
        show.setId(showId);
        show.setStatus(ShowStatusEnum.ARTIST_PROPOSAL);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setArtist(artist);
        show.setValue(100.0);
        show.setCoverCharge(5.0);

        // when
        Mockito.when(showRepository.findById(showId)).thenReturn(Optional.of(show));

        SecurityContextHolder.setContext(securityContextMock);
        Mockito.when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        Mockito.when(authenticationMock.getPrincipal()).thenReturn(userDetailsDto);

        Mockito.when(userRepository.findByEmail(artistEmail)).thenReturn(Optional.empty());

        // then
        EntityNotFoundException result = assertThrows(EntityNotFoundException.class, () -> service.acceptTermsOfNegotiation(showId));

        // assert
        assertEquals(errorMessage, result.getMessage());
        assertInstanceOf(EntityNotFoundException.class, result);
    }

    // confirmShow

    // withdrawNegotiation

    // concludeShow

    // cancelShow

    // listByStatus

    // listAllChangesByShowId

    // getById
}
