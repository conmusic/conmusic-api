package school.sptech.conmusicapi.modules.events.services;

import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.establishment.dtos.EstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.establishment.mappers.EstablishmentMapper;
import school.sptech.conmusicapi.modules.establishment.repositories.IEstablishmentRepository;
import school.sptech.conmusicapi.modules.establishment.specifications.EstablishmentSpecificationsBuilder;
import school.sptech.conmusicapi.modules.events.dtos.CreateEventDto;
import school.sptech.conmusicapi.modules.events.dtos.EventDto;
import school.sptech.conmusicapi.modules.events.dtos.UpdateEventDto;
import school.sptech.conmusicapi.modules.events.dtos.EventLineupExportDto;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.events.mappers.EventMapper;
import school.sptech.conmusicapi.modules.events.repositories.IEventRepository;
import school.sptech.conmusicapi.modules.events.specifications.EventSpecificationsBuilder;
import school.sptech.conmusicapi.modules.events.utils.datafiles.EventLineupResolver;
import school.sptech.conmusicapi.modules.genre.entities.Genre;
import school.sptech.conmusicapi.modules.genre.repository.IGenreRepository;
import school.sptech.conmusicapi.modules.show.entities.Show;
import school.sptech.conmusicapi.modules.show.repositories.IShowRepository;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;
import school.sptech.conmusicapi.shared.utils.collections.DeletionTree;
import school.sptech.conmusicapi.shared.utils.datafiles.DataFilesEnum;
import school.sptech.conmusicapi.shared.utils.datafiles.exporters.DataExporter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EventService {
    @Autowired
    private IEventRepository eventRepository;
    @Autowired
    private IEstablishmentRepository establishmentRepository;
    @Autowired
    private IGenreRepository genreRepository;
    @Autowired
    private EntityManager entityManager;
    public void filterForInactive(boolean isDeleted){
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedEventFilter");
        filter.setParameter("isDeleted", isDeleted);
        session.disableFilter("deletedProductFilter");
    }

    @Autowired
    private IShowRepository showRepository;

    public EventDto create(CreateEventDto dto) {
        Optional<Establishment> establishment = establishmentRepository.findById(dto.getEstablishmentId());

        if (establishment.isEmpty()) {
            throw new EntityNotFoundException(String.format("Establishment with id %d was not found", dto.getEstablishmentId()));
        }

        Optional<Genre> genre = genreRepository.findByNameIgnoreCase(dto.getGenre());

        if (genre.isEmpty()) {
            throw new EntityNotFoundException(String.format("Genre with name %s was not found", dto.getGenre()));
        }

        if (Objects.isNull(dto.getValue()) &&  Objects.isNull(dto.getCoverCharge())) {
            throw new BusinessRuleException("At least value or cover charge must be informed.");
        }

        Event event = EventMapper.fromDto(dto);
        event.setEstablishment(establishment.get());
        event.setGenre(genre.get());
        Event createdEvent = eventRepository.save(event);
        return EventMapper.toDto(createdEvent);
    }
    public EventDto updateEvent(UpdateEventDto dto, Integer id){
        Optional<Event> eventOpt = eventRepository.findById(id);

        if (eventOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("Event with id %d was not found.", id));
        }

        Optional<Genre> genreOpt = genreRepository.findByNameIgnoreCase(dto.getGenre());
        if (genreOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("genre was not found."));
        }

        Event updatedEstablishment = EventMapper.fromUpdateDto(dto, genreOpt.get() ,eventOpt.get());
        updatedEstablishment.setId(id);
        eventRepository.save(updatedEstablishment);
        return EventMapper.toDto(updatedEstablishment);
    }

    public List<EventDto> search(String value, Pageable pageable) {
        EventSpecificationsBuilder builder = new EventSpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(value + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3), null, null);
        }

        Specification<Event> spec = builder.build();

        List<Event> establishments = eventRepository.findAll(spec, pageable).getContent();

        return establishments.stream().map(EventMapper::toDto).toList();
    }

    public List<EventDto> listAll() {
        filterForInactive(false);
        return eventRepository.findAll()
                .stream()
                .map(EventMapper::toDto)
                .toList();
    }

    public List<EventDto> listAllByEstablishmentId(Integer id) {
        filterForInactive(false);
        return eventRepository.findByEstablishmentId(id)
                .stream()
                .map(EventMapper::toDto)
                .toList();
    }

    public List<EventDto> listAllAvailable(LocalDateTime date) {
        filterForInactive(false);
        return eventRepository.findBySchedulesStartDateTimeIsAfterAndSchedulesConfirmedFalse(date)
                .stream()
                .map(EventMapper::toDto)
                .toList();
    }

    public List<EventDto> listAllByManagerId(Integer id) {
        return eventRepository.findByEstablishmentManagerId(id)
                .stream()
                .map(EventMapper::toDto)
                .toList();
    }

    public EventDto getById(Integer id) {
        filterForInactive(false);
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                    String.format("Event with id %d was not found", id)
                ));

        return EventMapper.toDto(event);
    }
    
    public Iterable<EventDto> findAllInactive(){
        filterForInactive(true);
        List<EventDto> events =  eventRepository.findAll().stream().map(EventMapper:: toDto).toList();
        return (events);
    }

    public EventDto activateEvent(Integer id) {
        Optional<Event> eventOpt = eventRepository.findById(id);

        if (eventOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("Establishment with id %d was not found.", id));
        }
        Event eventInactive = EventMapper.fromInactive(eventOpt.get(), false);
        eventRepository.save(eventInactive);

        return EventMapper.toDto(eventInactive);
    }

    public String exportEventLineup(Integer id, DataFilesEnum fileFormat) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Event with id %d was not found", id)
                ));

        List<Show> shows = showRepository.findAllByStatusAndScheduleEventId(ShowStatusEnum.CONFIRMED, event.getId());

        DataExporter<EventLineupExportDto> exporter = EventLineupResolver.resolve(fileFormat);

        return exporter.write(shows.stream().map(EventMapper::toEventLineupExport).toList());
    }
}
