package school.sptech.conmusicapi.shared.utils.collections;

import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.establishment.dtos.EstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.establishment.mappers.EstablishmentMapper;
import school.sptech.conmusicapi.modules.establishment.repositories.IEstablishmentRepository;
import school.sptech.conmusicapi.modules.events.dtos.EventDto;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.events.mappers.EventMapper;
import school.sptech.conmusicapi.modules.events.repositories.IEventRepository;
import school.sptech.conmusicapi.modules.schedules.dtos.ScheduleDto;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;
import school.sptech.conmusicapi.modules.schedules.mappers.ScheduleMapper;
import school.sptech.conmusicapi.modules.schedules.repositories.IScheduleRepository;
import school.sptech.conmusicapi.shared.exceptions.EntityNotFoundException;
import school.sptech.conmusicapi.shared.utils.iterator.IGenericIterator;

import java.util.List;
import java.util.Optional;

@Service
public class DeletionTree {
    private NodeGen root;
    @Autowired
    private IScheduleRepository scheduleRepository;
    @Autowired
    private IEstablishmentRepository establishmentRepository;
    @Autowired
    private IEventRepository eventRepository;

    public NodeGen createRoot(Object info, TypeForDeletionEnum type) {
        if (info instanceof EstablishmentDto) {
            List<Event> eventList = eventRepository.findByEstablishmentId(((EstablishmentDto) info).getId());
            NodeGen newRoot = new NodeGen(info, type, eventList.size());
            root = newRoot;
            return newRoot;
        } else if(info instanceof EventDto){
            List<Schedule> scheduleList = scheduleRepository.findByEventId(((EventDto) info).getId());
            NodeGen newRoot = new NodeGen(info, type, scheduleList.size());
            root = newRoot;
            return newRoot;
        }
        return null;
    }

    public void insert(NodeGen dad) {
        if (root != null) {
            if (dad.getType() == TypeForDeletionEnum.ESTABLISHMENT && dad.getInfo() instanceof EstablishmentDto) {
                List<EventDto> eventlist = (eventRepository.findByEstablishmentId(((EstablishmentDto) dad.getInfo()).getId())).stream().map(EventMapper::toDto).toList();
                for (int i = 0; i < eventlist.size(); i++) {
                    NodeGen novo = new NodeGen(eventlist.get(i), TypeForDeletionEnum.EVENT, eventlist.size());
                    dad.getList().add(novo);
                    insert(novo);
                }
            } else if (dad.getType() == TypeForDeletionEnum.EVENT && dad.getInfo() instanceof EventDto) {
                List<ScheduleDto> scheduleList = scheduleRepository.findByEventId(((EventDto) dad.getInfo()).getId()).stream().map(ScheduleMapper::toDto).toList();
                for (int i = 0; i < scheduleList.size(); i++) {
                    NodeGen novo = new NodeGen(scheduleList.get(i), TypeForDeletionEnum.SCHEDULE, scheduleList.size());
                    dad.getList().add(novo);
                }
            }
        }
    }

    public NodeGen search(NodeGen dad, int id) {
        for (int i = 0; i < dad.getList().asList().size(); i++) {
            if (dad.getList().asList().get(i) instanceof NodeGen) {
                List<NodeGen> iterator = dad.getList().asList();
                if (((EventDto) iterator.get(i).getInfo()).getId() == id) {
                    return iterator.get(i);
                }
            }
        }
        return null;
    }

    public void deletionSequenceOnTree(NodeGen dad) {
        Boolean eventDeleted = false;
        if (dad.getType() == TypeForDeletionEnum.ESTABLISHMENT && dad.getInfo() instanceof EstablishmentDto) {
            inactivateEstablishment(((EstablishmentDto) dad.getInfo()).getId());
            for (int i = 0; i < dad.getList().getSize(); i++) {
                if (dad.getList().getByIndex(i) instanceof NodeGen) {
                    NodeGen event = ((NodeGen) dad.getList().getByIndex(i));
                    if (event.getInfo() instanceof EventDto) {
                        inactivateEvent(((EventDto) event.getInfo()).getId());
                        eventDeleted = true;
                        deletionSequenceOnTree(event);
                    }
                }
            }
        } else if (dad.getType() == TypeForDeletionEnum.EVENT) {
            if (!eventDeleted && dad.getInfo() instanceof EventDto){
                inactivateEvent(((EventDto) dad.getInfo()).getId());
            }
            for (int i = 0; i < dad.getList().getSize(); i++) {
                if (dad.getList().getByIndex(i) instanceof NodeGen) {
                    NodeGen schedule = ((NodeGen) dad.getList().getByIndex(i));
                    if (schedule.getInfo() instanceof ScheduleDto) {
                        inactivateSchedule(((ScheduleDto) schedule.getInfo()).getId());
                    }
                }
            }
        }
    }

    public ScheduleDto inactivateSchedule(Integer id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);

        if (schedule.isEmpty()) {
            throw new EntityNotFoundException(String.format("Establishment with id %d was not found.", id));
        }
        Schedule scheduleInactive = ScheduleMapper.fromInactive(schedule.get(), true);
        scheduleRepository.save(scheduleInactive);

        return ScheduleMapper.toDto(scheduleInactive);
    }

    public EventDto inactivateEvent(Integer id) {
        Optional<Event> eventOpt = eventRepository.findById(id);

        if (eventOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("Establishment with id %d was not found.", id));
        }
        Event eventInactive = EventMapper.fromInactive(eventOpt.get(), true);
        eventRepository.save(eventInactive);

        return EventMapper.toDto(eventInactive);
    }

    public EstablishmentDto inactivateEstablishment(Integer id) {
        Optional<Establishment> establishmentOpt = establishmentRepository.findById(id);

        if (establishmentOpt.isEmpty()) {
            throw new EntityNotFoundException(String.format("Establishment with id %d was not found.", id));
        }
        Establishment establishmentInactive = EstablishmentMapper.fromInactive(establishmentOpt.get(), true);
        establishmentRepository.save(establishmentInactive);

        return EstablishmentMapper.toDto(establishmentInactive);
    }

    public NodeGen getRoot() {
        return root;
    }

    public void setRoot(NodeGen root) {
        this.root = root;
    }


}

