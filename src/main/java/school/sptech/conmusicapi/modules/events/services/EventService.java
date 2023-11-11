package school.sptech.conmusicapi.modules.events.services;

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
        return eventRepository.findAll()
                .stream()
                .map(EventMapper::toDto)
                .toList();
    }

    public List<EventDto> listAllByEstablishmentId(Integer id) {
        return eventRepository.findByEstablishmentId(id)
                .stream()
                .map(EventMapper::toDto)
                .toList();
    }

    public List<EventDto> listAllAvailable(LocalDateTime date) {
        return eventRepository.findBySchedulesStartDateTimeIsAfterAndSchedulesConfirmedFalse(date)
                .stream()
                .map(EventMapper::toDto)
                .toList();
    }

    public EventDto getById(Integer id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                    String.format("Event with id %d was not found", id)
                ));

        return EventMapper.toDto(event);
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
