package school.sptech.conmusicapi.modules.event.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.event.dtos.CreateEventDto;
import school.sptech.conmusicapi.modules.event.dtos.EventDto;
import school.sptech.conmusicapi.modules.event.entities.Event;
import school.sptech.conmusicapi.modules.event.mapper.EventMapper;
import school.sptech.conmusicapi.modules.event.repositories.IEventRepository;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private IEventRepository eventRepository;

    public EventDto create(CreateEventDto dto) {

        if (dto.getPaymentValue() == null && dto.getCoverCharge() == null){
            throw new BusinessRuleException("At least payment value or cover charge must be informed.");
        }

        Event createdEvent = eventRepository.save(EventMapper.fromDto(dto));
        return EventMapper.toDto(createdEvent);
    }

    public List<EventDto> findAll() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(EventMapper::toDto).toList();
    }
}
