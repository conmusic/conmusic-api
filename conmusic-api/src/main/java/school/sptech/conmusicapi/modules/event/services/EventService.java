package school.sptech.conmusicapi.modules.event.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.event.dtos.CreateEventDto;
import school.sptech.conmusicapi.modules.event.dtos.EventDto;
import school.sptech.conmusicapi.modules.event.entities.Event;
import school.sptech.conmusicapi.modules.event.mapper.EventMapper;
import school.sptech.conmusicapi.modules.event.repositories.IEventRepository;

import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private IEventRepository eventRepository;

    public Optional<EventDto> create(CreateEventDto dto) {

        if (dto.getPaymentValue() == null && dto.getCoverCharge() == null){
            return Optional.empty();
        }

        Event createdEvent = eventRepository.save(EventMapper.fromDto(dto));
        return Optional.of(EventMapper.toDto(createdEvent));
    }
}
