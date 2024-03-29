package school.sptech.conmusicapi.modules.schedules.services;

import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.establishment.dtos.EstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.establishment.mappers.EstablishmentMapper;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.conmusicapi.modules.establishment.repositories.IEstablishmentRepository;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.events.repositories.IEventRepository;
import school.sptech.conmusicapi.modules.schedules.dtos.CreateScheduleDto;
import school.sptech.conmusicapi.modules.schedules.dtos.ReadScheduleDto;
import school.sptech.conmusicapi.modules.schedules.dtos.ScheduleDto;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;
import school.sptech.conmusicapi.modules.schedules.mappers.ScheduleMapper;
import school.sptech.conmusicapi.modules.schedules.repositories.IScheduleRepository;
import school.sptech.conmusicapi.modules.schedules.utils.ScheduleUtil;
import school.sptech.conmusicapi.modules.schedules.utils.datafiles.ScheduleImporterResolver;
import school.sptech.conmusicapi.shared.exceptions.BusinessRuleException;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;
import school.sptech.conmusicapi.shared.exceptions.FailedImportException;
import school.sptech.conmusicapi.shared.utils.datafiles.importers.DataImporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    private IEventRepository eventRepository;

    @Autowired
    private IEstablishmentRepository establishmentRepository;

    @Autowired
    private IScheduleRepository scheduleRepository;

    @Autowired
    private EntityManager entityManager;
    public void filterForInactive(boolean isDeleted){
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedScheduleFilter");
        filter.setParameter("isDeleted", isDeleted);
        session.disableFilter("deletedProductFilter");
    }
    public ScheduleDto create(CreateScheduleDto dto, Integer eventId) {
        Schedule schedule = ScheduleMapper.fromDto(dto);

        if (ScheduleUtil.isDurationIntervalInvalid(schedule)) {
            throw new BusinessRuleException("Schedule with invalid dates. Start DateTime MUST be before End DateTime");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Event with id %d was not found", eventId)));

        schedule.setEvent(event);

        for (Schedule s : event.getSchedules()) {
            if (ScheduleUtil.isScheduleOverlappingOtherSchedule(schedule, s)) {
                throw new BusinessRuleException("Schedule is overlapping other schedule.");
            }
        }

        Schedule createdSchedule = scheduleRepository.save(schedule);
        return ScheduleMapper.toDto(createdSchedule);
    }

    public List<ScheduleDto> listByEventId(Integer id) {
        filterForInactive(false);
        if (!eventRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Event with id %d was not found", id));
        }

        List<Schedule> schedules = scheduleRepository.findByEventId(id);

        return schedules
                .stream()
                .map(ScheduleMapper::toDto)
                .toList();
    }

    public List<ScheduleDto> listByEstablishmentId(Integer id) {
        filterForInactive(false);
        if (!establishmentRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Establishment with id %d was not found", id));
        }

        List<Schedule> schedules = scheduleRepository.findByEventEstablishmentId(id);

        return schedules
                .stream()
                .map(ScheduleMapper::toDto)
                .toList();
    }
    public Iterable<ScheduleDto> findAllInactive(){
        filterForInactive(true);
        List<ScheduleDto> schedules =  scheduleRepository.findAll().stream().map(ScheduleMapper :: toDto).toList();
        return (schedules);
    }

    public ScheduleDto activateSchedule(Integer id){
        Optional<Schedule> schedule = scheduleRepository.findById(id);

        if (schedule.isEmpty()) {
            throw new EntityNotFoundException(String.format("Establishment with id %d was not found.", id));
        }
        Schedule scheduleInactive = ScheduleMapper.fromInactive(schedule.get(), false);
        scheduleRepository.save(scheduleInactive);

        return ScheduleMapper.toDto(scheduleInactive);
    }

    public List<ScheduleDto> importSchedules(MultipartFile file) throws RuntimeException {
        List<ScheduleDto> importedSchedules = new ArrayList<>();

        try {
            DataImporter<ReadScheduleDto> importer = ScheduleImporterResolver.resolve(file.getResource().getFilename());
            List<ReadScheduleDto> readSchedules = importer.read(file.getInputStream());

            for (ReadScheduleDto schedule : readSchedules) {
                CreateScheduleDto createDto = ScheduleMapper.toCreateDto(schedule);
                importedSchedules.add(create(createDto, schedule.getEventId()));
            }
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new FailedImportException(e.getMessage());
        }

        return importedSchedules;
    }
}
