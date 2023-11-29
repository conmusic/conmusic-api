package school.sptech.conmusicapi.shared.applicationevents;

import org.springframework.context.ApplicationEvent;

public class ShowRecordConsistencyEvent extends ApplicationEvent {
    public ShowRecordConsistencyEvent(Object source) {
        super(source);
    }
}
