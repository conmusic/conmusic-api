package school.sptech.conmusicapi.shared.utils.collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.establishment.dtos.EstablishmentDto;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.establishment.repositories.IEstablishmentRepository;
import school.sptech.conmusicapi.modules.events.dtos.EventDto;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.events.mappers.EventMapper;
import school.sptech.conmusicapi.modules.events.repositories.IEventRepository;
import school.sptech.conmusicapi.modules.schedules.dtos.ScheduleDto;
import school.sptech.conmusicapi.modules.schedules.mappers.ScheduleMapper;
import school.sptech.conmusicapi.modules.schedules.repositories.IScheduleRepository;
import school.sptech.conmusicapi.shared.utils.iterator.IGenericIterator;

import java.util.List;

@Service
public class DeletionTree {
    private NodeGen root;
    @Autowired
    private IScheduleRepository scheduleRepository;
    @Autowired
    private IEstablishmentRepository establishmentRepository;
    @Autowired
    private IEventRepository eventRepository;

    public NodeGen createRoot(EstablishmentDto info, TypeForDeletionEnum type) {
        List<Event> eventList = eventRepository.findByEstablishmentId(info.getId());
        NodeGen newRoot = new NodeGen(info, type, eventList.size());
        root = newRoot;
        return newRoot;
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

    public EventDto search(NodeGen dad, int id) {
        if (dad.getInfo() instanceof EventDto && dad.getList().createIterator().getNext() instanceof NodeGen) {
            IGenericIterator<NodeGen> iterator = dad.getList().createIterator();
                while (iterator.hasMore()) {
                    if (((EventDto) iterator.getNext().getInfo()).getId() == id) {
                        return ((EventDto) iterator.getNext().getInfo());
                    }
                }
            }
        return null;
    }


//    public void exibeArvore(NodeGen noDaVez) {
//        if (noDaVez != null && noDaVez.getnext() != null) {
//            System.out.printf("Valor do nó: " + noDaVez.getInfo() + "  ");
//            System.out.println("Valor seguinte do nó: " + noDaVez.getnext().getInfo() + ";");
//            exibeArvore(noDaVez.getnext());
//        }
//    }

    public NodeGen getRoot() {
        return root;
    }

    public void setRoot(NodeGen root) {
        this.root = root;
    }

}
