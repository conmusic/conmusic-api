package school.sptech.conmusicapi.shared.applicationevents;

import org.springframework.context.ApplicationEvent;

public class StatusConsistencyEvent extends ApplicationEvent {
    public StatusConsistencyEvent(Object source) {
        super(source);
    }
}
