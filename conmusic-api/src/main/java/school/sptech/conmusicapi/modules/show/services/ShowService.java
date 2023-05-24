package school.sptech.conmusicapi.modules.show.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.artist.entities.Artist;
import school.sptech.conmusicapi.modules.artist.repositories.IArtistRepository;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.events.repositories.IEventRepository;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;
import school.sptech.conmusicapi.modules.schedules.repositories.IScheduleRepository;
import school.sptech.conmusicapi.modules.show.dtos.CreateShowDto;
import school.sptech.conmusicapi.modules.show.dtos.ShowDto;
import school.sptech.conmusicapi.modules.show.dtos.UpdateShowDto;
import school.sptech.conmusicapi.modules.show.entities.Show;
import school.sptech.conmusicapi.modules.show.entities.ShowRecord;
import school.sptech.conmusicapi.modules.show.mapper.ShowMapper;
import school.sptech.conmusicapi.modules.show.repositories.IShowRecordRepository;
import school.sptech.conmusicapi.modules.show.repositories.IShowRepository;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;
import school.sptech.conmusicapi.modules.show.util.ShowUtil;
import school.sptech.conmusicapi.modules.user.dtos.UserDetailsDto;
import school.sptech.conmusicapi.modules.user.entities.User;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;
import school.sptech.conmusicapi.shared.utils.collections.GenericObjectQueue;
import school.sptech.conmusicapi.shared.utils.collections.GenericObjectStack;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {
    @Autowired
    private IShowRepository showRepository;

    @Autowired
    private IArtistRepository artistRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IEventRepository eventRepository;

    @Autowired
    private IScheduleRepository scheduleRepository;

    @Autowired
    private IShowRecordRepository showRecordRepository;

    private GenericObjectStack<Integer> stack = new GenericObjectStack<Integer>();

    public ShowDto create(CreateShowDto dto) {
        Optional<Show> showOpt = showRepository.findByArtistIdAndEventIdAndScheduleId(
                dto.getArtistId(),
                dto.getEventId(),
                dto.getScheduleId()
        );

        Show show = ShowMapper.fromDto(dto);
        if (showOpt.isEmpty()) {
            Artist artist = artistRepository.findById(dto.getArtistId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Artist with id %d was not found", dto.getArtistId())));

            Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Event with id %d was not found", dto.getEventId())));

            Schedule schedule = scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Schedule with id %d was not found", dto.getScheduleId())));

            if (
                    schedule.getStartDateTime().isBefore(LocalDateTime.now())
                    || schedule.getStartDateTime().equals(LocalDateTime.now())
            ) {
                throw new BusinessRuleException("The date of this show has already happened.");
            }

            if (schedule.getConfirmed()) {
                throw new BusinessRuleException("Schedule is already confirmed");
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsDto details = (UserDetailsDto) authentication.getPrincipal();

            ShowStatusEnum status = ShowStatusEnum.getStatusByName(String.format("%S_PROPOSAL", details.getUserType()));

            show.setArtist(artist);
            show.setEvent(event);
            show.setSchedule(schedule);
            show.setStatus(status);
        } else {
            show.setId(showOpt.get().getId());
            show.setArtist(showOpt.get().getArtist());
            show.setEvent(showOpt.get().getEvent());
            show.setSchedule(showOpt.get().getSchedule());
            show.setStatus(ShowStatusEnum.NEGOTIATION);
        }

        Show createdShow = showRepository.save(show);
        return ShowMapper.toDto(createdShow);
    }

    public ShowDto update(int id, UpdateShowDto dto) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Show with id %d was not found", id)
                ));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDto details = (UserDetailsDto) authentication.getPrincipal();

        User user = userRepository.findByEmail(details.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with email %s was not found", details.getUsername())
                ));

        if (
            !show.getEvent().getEstablishment().getManager().getId().equals(user.getId())
            && !show.getArtist().getId().equals(user.getId())
        ) {
            throw new BusinessRuleException("Unrelated users cannot request for changes");
        }

        if (
                show.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())
                || show.getSchedule().getStartDateTime().equals(LocalDateTime.now())
        ) {
            throw new BusinessRuleException("The date of this show has already happened.");
        }

        if (
                show.getStatus().equals(ShowStatusEnum.CONFIRMED)
                || show.getStatus().equals(ShowStatusEnum.CONCLUDED)
        ) {
            throw new BusinessRuleException("The details of this show are already agreed between the parties");
        }

        if (
                !show.getStatus().equals(ShowStatusEnum.NEGOTIATION)
                && !show.getStatus().equals(ShowStatusEnum.ARTIST_ACCEPTED)
                && !show.getStatus().equals(ShowStatusEnum.MANAGER_ACCEPTED)
        ) {
            throw new BusinessRuleException("It's not possible to negotiate the details of this show");
        }

        ShowRecord record = showRecordRepository.save(ShowUtil.createRecord(show, user));


        ShowMapper.fromDtoUpdate(dto, show);
        show.setStatus(ShowStatusEnum.NEGOTIATION);

        Show updatedShow = showRepository.save(show);
        return ShowMapper.toDto(updatedShow);
    }

    public ShowDto updateStatus(int id, String newStatus) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Show with id %d was not found", id)
                ));

        if (
                newStatus.equals("ACCEPTED")
                || newStatus.equals("REJECTED")
                || newStatus.equals("WITHDRAW")
                || newStatus.equals("CANCELED")
        ) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsDto details = (UserDetailsDto) authentication.getPrincipal();

            if (!details.getUserType().equals("Artist") && !details.getUserType().equals("Manager")) {
                throw new BusinessRuleException("Invalid user requesting changes for show");
            }

            newStatus = String.format("%S_%S", details.getUserType(), newStatus);
        } else if (
                !newStatus.equals("NEGOTIATION")
                && !newStatus.equals("CONFIRMED")
                && !newStatus.equals("CONCLUDED")
        ) {
            throw new BusinessRuleException(String.format("Invalid newStatus: %s", newStatus));
        }

        ShowStatusEnum status = ShowStatusEnum.getStatusByName(newStatus);
        if (status.equals(ShowStatusEnum.UNDEFINED)) {
            throw new BusinessRuleException(String.format("Invalid newStatus: %s", newStatus));
        }

        if (!show.getStatus().isStatusChangeValid(status)) {
            throw new BusinessRuleException(String.format(
                    "It is forbidden to change status from %s to %s",
                    show.getStatus().name(),
                    status.name()
            ));
        }

        show.setStatus(status);
        Show updatedShow = showRepository.save(show);
        return ShowMapper.toDto(updatedShow);
    }
}
