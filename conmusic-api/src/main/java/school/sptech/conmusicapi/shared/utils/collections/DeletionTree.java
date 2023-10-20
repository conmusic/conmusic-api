package school.sptech.conmusicapi.shared.utils.collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;
import school.sptech.conmusicapi.modules.establishment.repositories.IEstablishmentRepository;
import school.sptech.conmusicapi.modules.establishment.services.EstablishmentService;
import school.sptech.conmusicapi.modules.events.dtos.EventDto;
import school.sptech.conmusicapi.modules.events.entities.Event;
import school.sptech.conmusicapi.modules.events.repositories.IEventRepository;
import school.sptech.conmusicapi.modules.events.services.EventService;
import school.sptech.conmusicapi.modules.schedules.dtos.ScheduleDto;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;
import school.sptech.conmusicapi.modules.schedules.repositories.IScheduleRepository;
import school.sptech.conmusicapi.modules.schedules.services.ScheduleService;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;
@Service
public class DeletionTree {
    private NodeGen root;
    private IScheduleRepository scheduleService;
    private IEstablishmentRepository establishmentService;
    private IEventRepository eventService;

    public void createRoot(Object info, TypeForDeletionEnum type){
        NodeGen newRoot = new NodeGen(info, type);
        root = newRoot;
    }

    public static int findNodeLevel(NodeGen root) {
        if (root == null) {
            return 0;
        } else {
            int leftDepth = findNodeLevel(root.getprev());
            int rightDepth = findNodeLevel(root.getnext());
            return Math.max(leftDepth, rightDepth) + 1;
        }
    }
    public void insert(NodeGen dad){
        if (root != null) {
            if (dad.getType() == TypeForDeletionEnum.ESTABLISHMENT && dad.getInfo() instanceof Establishment){
                List<Event> eventlist = eventService.findByEstablishmentId(((Establishment) dad.getInfo()).getId());
                    for (Event evt: eventlist) {
                        NodeGen novo = new NodeGen(eventlist, TypeForDeletionEnum.EVENT);
                        dad.setnext(novo);
                }
            } else if (dad.getType() == TypeForDeletionEnum.EVENT && dad.getInfo() instanceof Event){
                List<Schedule> scheduleList = scheduleService.findByEventId(((Event) dad.getInfo()).getId());
                for (Schedule schedule : scheduleList){
                    NodeGen novo = new NodeGen(scheduleList, TypeForDeletionEnum.SCHEDULE);
                    dad.setnext(novo);
                }
            }
        }
    }

    public EventDto serch(NodeGen root, int id) {
        if (root == null) {
            return null;
        }

        if (root.getInfo() instanceof Event && (((Event) root.getInfo()).getId() == id)) {
            return ((EventDto) root.getInfo());
        }

        while (root != null){
            root = root.getnext();
            EventDto resultado = serch(root, id);
            if (resultado != null) {
                return resultado;
            }
        }
        return null;
    }

    public boolean deletionTree(NodeGen dad){

    }
    public void exibeArvore(NodeGen noDaVez) {
        if (noDaVez != null && noDaVez.getnext() != null) {
            System.out.printf("Valor do nó: " + noDaVez.getInfo() + "  ");
            System.out.println("Valor seguinte do nó: " + noDaVez.getnext().getInfo() + ";");
            exibeArvore(noDaVez.getnext());
        }
    }

    public NodeGen getRoot() {
        return root;
    }

    public void setRoot(NodeGen root) {
        this.root = root;
    }

}
