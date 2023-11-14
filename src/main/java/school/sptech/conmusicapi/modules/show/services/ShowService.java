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
import school.sptech.conmusicapi.modules.show.dtos.ShowRecordDto;
import school.sptech.conmusicapi.modules.show.dtos.UpdateShowDto;
import school.sptech.conmusicapi.modules.show.entities.Show;
import school.sptech.conmusicapi.modules.show.entities.ShowRecord;
import school.sptech.conmusicapi.modules.show.mapper.ShowMapper;
import school.sptech.conmusicapi.modules.show.mapper.ShowRecordMapper;
import school.sptech.conmusicapi.modules.show.repositories.IShowRecordRepository;
import school.sptech.conmusicapi.modules.show.repositories.IShowRepository;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;
import school.sptech.conmusicapi.modules.show.util.ShowUtil;
import school.sptech.conmusicapi.modules.user.dtos.UserDetailsDto;
import school.sptech.conmusicapi.modules.user.entities.User;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;
import school.sptech.conmusicapi.shared.utils.collections.GenericObjectCircularQueue;
import school.sptech.conmusicapi.shared.utils.collections.GenericObjectList;
import school.sptech.conmusicapi.shared.utils.collections.GenericObjectStack;
import school.sptech.conmusicapi.shared.utils.iterator.IGenericIterator;

import java.time.LocalDateTime;
import java.util.*;

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

    public ShowDto create(CreateShowDto dto) {
        Optional<Show> showOpt = showRepository.findByArtistIdAndEventIdAndScheduleId(
                dto.getArtistId(),
                dto.getEventId(),
                dto.getScheduleId()
        );

        if (showOpt.isPresent()) {
            return this.acceptProposal(showOpt.get().getId());
        }

        Show show = ShowMapper.fromDto(dto);

        Artist artist = artistRepository.findById(dto.getArtistId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Artist with id %d was not found", dto.getArtistId())));

        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Event with id %d was not found", dto.getEventId())));

        Schedule schedule = scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Schedule with id %d was not found", dto.getScheduleId())));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDto details = (UserDetailsDto) authentication.getPrincipal();

        User user = userRepository.findByEmail(details.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with email %s was not found", details.getUsername())));

        if (
                !artist.getId().equals(user.getId())
                && !event.getEstablishment().getManager().getId().equals(user.getId())
        ) {
            throw new BusinessRuleException("The user is neither the artist nor the manager of the show");
        }

        if (!schedule.getEvent().getId().equals(event.getId())) {
            throw new BusinessRuleException("Schedule is not from the event that was informed");
        }

        if (
                schedule.getStartDateTime().isBefore(LocalDateTime.now())
                        || schedule.getStartDateTime().equals(LocalDateTime.now())
        ) {
            throw new BusinessRuleException("The date of this show has already happened.");
        }

        if (schedule.getConfirmed()) {
            throw new BusinessRuleException("Schedule is already confirmed");
        }

        ShowStatusEnum status = ShowStatusEnum.getStatusByName(String.format("%S_PROPOSAL", details.getUserType()));
        if (status.equals(ShowStatusEnum.UNDEFINED)) {
            throw new BusinessRuleException("Invalid show status");
        }

        show.setArtist(artist);
        show.setEvent(event);
        show.setSchedule(schedule);
        show.setStatus(status);

        Show createdShow = showRepository.save(show);

        showRecordRepository.save(ShowUtil.createRecord(createdShow, user));
        return ShowMapper.toDto(createdShow);
    }

    public ShowDto update(Integer id, UpdateShowDto dto) {
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

        ShowMapper.fromDtoUpdate(dto, show);
        show.setStatus(ShowStatusEnum.NEGOTIATION);

        Show updatedShow = showRepository.save(show);
        showRecordRepository.save(ShowUtil.createRecord(updatedShow, user));
        return ShowMapper.toDto(updatedShow);
    }

    public ShowDto acceptProposal(Integer id) {
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

        if (show.getStatus().equals(ShowStatusEnum.NEGOTIATION)) {
            throw new BusinessRuleException("Show is already in negotiation");
        }

        if (!show.getStatus().isStatusChangeValid(ShowStatusEnum.NEGOTIATION)) {
            throw new BusinessRuleException(String.format(
                    "It is forbidden to change status from %s to %s",
                    show.getStatus().name(),
                    ShowStatusEnum.NEGOTIATION
            ));
        }

        if (show.getStatus().name().contains(details.getUserType().toUpperCase())) {
            throw new BusinessRuleException("The user who made the proposal cannot accept it");
        }

        if (
                show.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())
                || show.getSchedule().getStartDateTime().equals(LocalDateTime.now())
        ) {
            show.setStatus(ShowStatusEnum.getStatusByName(String.format("%S_REJECTED", details.getUserType())));
            Show updatedShow = showRepository.save(show);
            showRecordRepository.save(ShowUtil.createRecord(updatedShow, user));

            throw new BusinessRuleException("The proposal date has passed");
        }

        if (show.getSchedule().getConfirmed()) {
            show.setStatus(ShowStatusEnum.MANAGER_REJECTED);
            Show updatedShow = showRepository.save(show);
            showRecordRepository.save(ShowUtil.createRecord(updatedShow, user));

            throw new BusinessRuleException("The schedule already has one show confirmed");
        }

        show.setStatus(ShowStatusEnum.NEGOTIATION);
        Show updatedShow = showRepository.save(show);
        showRecordRepository.save(ShowUtil.createRecord(updatedShow, user));
        return ShowMapper.toDto(updatedShow);
    }

    public void rejectProposal(Integer id) {
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

        ShowStatusEnum status = ShowStatusEnum.getStatusByName(String.format("%S_REJECTED", details.getUserType()));
        if (!show.getStatus().isStatusChangeValid(status)) {
            throw new BusinessRuleException(String.format(
                    "It is forbidden to change status from %s to %s",
                    show.getStatus().name(),
                    status
            ));
        }

        show.setStatus(status);
        Show updatedShow = showRepository.save(show);
        showRecordRepository.save(ShowUtil.createRecord(updatedShow, user));
    }

    public ShowDto acceptTermsOfNegotiation(Integer id) {
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

        ShowStatusEnum status = ShowStatusEnum.getStatusByName(String.format("%S_ACCEPTED", details.getUserType()));
        if (show.getStatus().equals(status)) {
            throw new BusinessRuleException("Both have to accept the terms of negotiation for the show to be confirmed");
        } else if (
                show.getStatus().equals(ShowStatusEnum.MANAGER_ACCEPTED)
                || show.getStatus().equals(ShowStatusEnum.ARTIST_ACCEPTED)
        ) {
            return this.confirmShow(id);
        } else if (!show.getStatus().isStatusChangeValid(status)) {
            throw new BusinessRuleException(String.format(
                    "It is forbidden to change status from %s to %s",
                    show.getStatus().name(),
                    status
            ));
        }

        if (
                show.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())
                || show.getSchedule().getStartDateTime().equals(LocalDateTime.now())
        ) {
            show.setStatus(ShowStatusEnum.getStatusByName(String.format("%S_WITHDRAW", details.getUserType())));
            Show updatedShow = showRepository.save(show);
            showRecordRepository.save(ShowUtil.createRecord(updatedShow, user));

            throw new BusinessRuleException("The schedule date has already passed");
        }

        if (show.getSchedule().getConfirmed()) {
            show.setStatus(ShowStatusEnum.MANAGER_WITHDRAW);
            Show updatedShow = showRepository.save(show);
            showRecordRepository.save(ShowUtil.createRecord(updatedShow, user));

            throw new BusinessRuleException("Schedule has already one show confirmed");
        }

        show.setStatus(status);
        Show updatedShow = showRepository.save(show);
        showRecordRepository.save(ShowUtil.createRecord(updatedShow, user));
        return ShowMapper.toDto(updatedShow);
    }

    public ShowDto confirmShow(Integer id) {
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
                !show.getStatus().equals(ShowStatusEnum.MANAGER_ACCEPTED)
                && !show.getStatus().equals(ShowStatusEnum.ARTIST_ACCEPTED)
        ) {
            throw new BusinessRuleException(String.format(
                    "It is forbidden to change status from %s to %s",
                    show.getStatus().name(),
                    ShowStatusEnum.CONFIRMED
            ));
        }

        if (
                show.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())
                        || show.getSchedule().getStartDateTime().equals(LocalDateTime.now())
        ) {
            show.setStatus(ShowStatusEnum.getStatusByName(String.format("%S_WITHDRAW", details.getUserType())));
            Show updatedShow = showRepository.save(show);
            showRecordRepository.save(ShowUtil.createRecord(updatedShow, user));

            throw new BusinessRuleException("The schedule date has already passed");
        }

        if (show.getSchedule().getConfirmed()) {
            show.setStatus(ShowStatusEnum.MANAGER_WITHDRAW);
            Show updatedShow = showRepository.save(show);
            showRecordRepository.save(ShowUtil.createRecord(updatedShow, user));

            throw new BusinessRuleException("Schedule has already one show confirmed");
        }

        show.setStatus(ShowStatusEnum.CONFIRMED);
        Show updatedShow = showRepository.save(show);
        ShowRecord record = ShowUtil.createRecord(updatedShow, user);

        List<ShowRecord> records = new ArrayList<>();
        records.add(record);

        List<Show> otherShowWithSameSchedule = showRepository.findByIdNotAndScheduleIdEquals(show.getId(), show.getSchedule().getId());

        otherShowWithSameSchedule.forEach(s -> s.setStatus(ShowStatusEnum.MANAGER_WITHDRAW));
        records.addAll(otherShowWithSameSchedule.stream().map(s -> ShowUtil.createRecord(s, user)).toList());

        showRecordRepository.saveAll(records);
        showRepository.saveAll(otherShowWithSameSchedule);

        Schedule scheduleToUpdate = show.getSchedule();
        scheduleToUpdate.setConfirmed(true);
        scheduleRepository.save(scheduleToUpdate);

        return ShowMapper.toDto(updatedShow);
    }

    public void withdrawNegotiation(Integer id) {
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

        ShowStatusEnum status = ShowStatusEnum.getStatusByName(String.format("%S_WITHDRAW", details.getUserType()));
        if (!show.getStatus().isStatusChangeValid(status)) {
            throw new BusinessRuleException(String.format(
                    "It is forbidden to change status from %s to %s",
                    show.getStatus().name(),
                    status
            ));
        }

        show.setStatus(status);
        Show updatedShow = showRepository.save(show);
        showRecordRepository.save(ShowUtil.createRecord(updatedShow, user));
    }

    public ShowDto concludeShow(Integer id) {
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

        if (!show.getStatus().isStatusChangeValid(ShowStatusEnum.CONCLUDED)) {
            throw new BusinessRuleException(String.format(
                    "It is forbidden to change status from %s to %s",
                    show.getStatus().name(),
                    ShowStatusEnum.CONCLUDED
            ));
        }

        if (show.getSchedule().getEndDateTime().isAfter(LocalDateTime.now())) {
            throw new BusinessRuleException("Show has not finished yet.");
        }

        show.setStatus(ShowStatusEnum.CONCLUDED);
        Show updatedShow = showRepository.save(show);
        showRecordRepository.save(ShowUtil.createRecord(updatedShow, user));
        return ShowMapper.toDto(updatedShow);
    }

    public void cancelShow(Integer id) {
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

        ShowStatusEnum status = ShowStatusEnum.getStatusByName(String.format("%S_CANCELED", details.getUserType()));
        if (!show.getStatus().isStatusChangeValid(status)) {
            throw new BusinessRuleException(String.format(
                    "It is forbidden to change status from %s to %s",
                    show.getStatus().name(),
                    status.name()
            ));
        }

        show.setStatus(status);
        Show updatedShow = showRepository.save(show);
        showRecordRepository.save(ShowUtil.createRecord(updatedShow,user));

        Schedule schedule = show.getSchedule();
        schedule.setConfirmed(false);
        scheduleRepository.save(schedule);
    }

    public List<ShowDto> listByStatus(EnumSet status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDto details = (UserDetailsDto) authentication.getPrincipal();

        User user = userRepository.findByEmail(details.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("User with email %s was not found", details.getUsername())
                ));

        List<Show> shows = showRepository.findAllByUserIdAndStatus(user.getId(), status);
        GenericObjectList<Show> list = new GenericObjectList<>(shows.size());
        shows.forEach(list::add);

        GenericObjectCircularQueue<ShowDto> queue = new GenericObjectCircularQueue<>(list.getSize());
        IGenericIterator<Show> iterator = list.createIterator();
        while (iterator.hasMore()) {
            queue.insert(ShowMapper.toDto(iterator.getNext()));
        }

        return queue.asList();
    }

    public List<ShowRecordDto> listAllChangesByShowId(Integer id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Show with id %d was not found", id)
                ));

        List<ShowRecord> records = showRecordRepository.findByShowId(show.getId());
        GenericObjectList<ShowRecord> list = new GenericObjectList<ShowRecord>(records.size());
        records.forEach(list::add);

        GenericObjectStack<ShowRecordDto> stack = new GenericObjectStack<>(list.getSize());
        IGenericIterator<ShowRecord> iterator = list.createIterator();
        while (iterator.hasMore()) {
            stack.push(ShowRecordMapper.toDto(iterator.getNext()));
        }

        return stack.asList();
    }

    public ShowDto getById(Integer id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Show with id %d was not found", id)
                ));

        return ShowMapper.toDto(show);
    }
}
