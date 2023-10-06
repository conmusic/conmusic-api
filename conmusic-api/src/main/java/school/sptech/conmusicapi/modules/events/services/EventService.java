package school.sptech.conmusicapi.modules.events.services;

import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.establishment.dtos.EstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.establishment.mappers.EstablishmentMapper;
import school.sptech.conmusicapi.modules.establishment.repositories.IEstablishmentRepository;
import school.sptech.conmusicapi.modules.events.dtos.CreateEventDto;
import school.sptech.conmusicapi.modules.events.dtos.EventDto;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.events.mappers.EventMapper;
import school.sptech.conmusicapi.modules.events.repositories.IEventRepository;
import school.sptech.conmusicapi.modules.genre.entities.Genre;
import school.sptech.conmusicapi.modules.genre.repository.IGenreRepository;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        Filter filter = session.enableFilter("deletedEstablishmentFilter");
        filter.setParameter("isDeleted", isDeleted);
        session.disableFilter("deletedProductFilter");
    }

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
    public Iterable<EventDto> findAllInactive(){
        filterForInactive(true);
        List<EventDto> events =  eventRepository.findAll().stream().map(EventMapper:: toDto).toList();
        return (events);
    }
    public EventDto inactivateEstablishment(Integer id){
        Optional<Event> eventOpt = eventRepository.findById(id);

        if (eventOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("Establishment with id %d was not found.", id));
        }
        Event eventInactive = EventMapper.fromInactive(eventOpt.get(), true);
        eventRepository.save(eventInactive);

        return EventMapper.toDto(eventInactive);
    }

    public EventDto activateEstablishment(Integer id){
        Optional<Event> eventOpt = eventRepository.findById(id);

        if (eventOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("Establishment with id %d was not found.", id));
        }
        Event eventInactive = EventMapper.fromInactive(eventOpt.get(), false);
        eventRepository.save(eventInactive);

        return EventMapper.toDto(eventInactive);
    }
}
