package school.sptech.conmusicapi.modules.events.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.establishment.repositories.IEstablishmentRepository;
import school.sptech.conmusicapi.modules.events.dtos.CreateEventDto;
import school.sptech.conmusicapi.modules.events.dtos.EventDto;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.events.mappers.EventMapper;
import school.sptech.conmusicapi.modules.events.repositories.IEventRepository;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;

import java.time.Duration;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private IEventRepository eventRepository;
    @Autowired
    private IEstablishmentRepository establishmentRepository;

    public EventDto create(CreateEventDto dto) {
        Optional<Establishment> establishment = establishmentRepository.findById(dto.getEstablishmentId());

        if (establishment.isEmpty()) {
            throw new EntityNotFoundException(String.format("Establishment with id %d was not found", dto.getEstablishmentId()));
        }

        if (dto.getValue() == null && dto.getCoverCharge() == null) {
            throw new BusinessRuleException("At least value or cover charge must be informed.");
        }

        if (dto.getUnique()) {
            if (dto.getStartDate() == null || dto.getEndDate() == null) {
                throw new BusinessRuleException("Unique events must inform a start date and end date.");
            }

            if (Duration.between(dto.getStartDate(), dto.getEndDate()).toMinutes() < 30) {
                throw new BusinessRuleException("An event cannot have a duration under 30 minutes");
            }
        }

        Event event = EventMapper.fromDto(dto);
        event.setEstablishment(establishment.get());
        Event createdEvent = eventRepository.save(event);
        return EventMapper.toDto(createdEvent);
    }
}
