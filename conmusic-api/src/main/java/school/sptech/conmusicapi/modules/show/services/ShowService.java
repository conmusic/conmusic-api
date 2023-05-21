package school.sptech.conmusicapi.modules.show.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.artist.entities.Artist;
import school.sptech.conmusicapi.modules.artist.repositories.IArtistRepository;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.events.repositories.IEventRepository;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;
import school.sptech.conmusicapi.modules.schedules.repositories.IScheduleRepository;
import school.sptech.conmusicapi.modules.show.dtos.CreateShowDto;
import school.sptech.conmusicapi.modules.show.dtos.ShowDto;
import school.sptech.conmusicapi.modules.show.entities.Show;
import school.sptech.conmusicapi.modules.show.mapper.ShowMapper;
import school.sptech.conmusicapi.modules.show.repositories.IShowRepository;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;

import java.util.Optional;

@Service
public class ShowService {
    @Autowired
    private IShowRepository showRepository;

    @Autowired
    private IArtistRepository artistRepository;

    @Autowired
    private IEventRepository eventRepository;

    @Autowired
    private IScheduleRepository scheduleRepository;

    public ShowDto create(CreateShowDto dto) {
        Optional<Show> showOpt = showRepository.findByArtistIdAndEventIdAndScheduleId(
                dto.getArtistId(),
                dto.getEventId(),
                dto.getScheduleId()
        );

        Show show = ShowMapper.fromDto(dto);
        if (showOpt.isEmpty()) {
            if (show.getStatus().equals(ShowStatusEnum.UNDEFINED)) {
                throw new BusinessRuleException("Show Status is invalid");
            }

            Artist artist = artistRepository.findById(dto.getArtistId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Artist with id %d was not found", dto.getArtistId())));

            Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Event with id %d was not found", dto.getEventId())));

            Schedule schedule = scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Schedule with id %d was not found", dto.getScheduleId())));

            show.setArtist(artist);
            show.setEvent(event);
            show.setSchedule(schedule);
        }

        Show createdShow = showRepository.save(show);
        return ShowMapper.toDto(createdShow);
    }
}
