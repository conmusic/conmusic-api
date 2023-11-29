package school.sptech.conmusicapi.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import school.sptech.conmusicapi.modules.show.entities.Show;
import school.sptech.conmusicapi.modules.show.repositories.IShowRepository;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;
import school.sptech.conmusicapi.shared.applicationevents.ShowRecordConsistencyEvent;
import school.sptech.conmusicapi.shared.applicationevents.StatusConsistencyEvent;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;

@Component
public class StatusConsistencyJob {
    @Autowired
    private IShowRepository showRepository;
    @Autowired
    private ApplicationEventPublisher appEventPublisher;

    @EventListener(StatusConsistencyEvent.class)
    public void run() {
        System.out.println("["+ this.getClass().getSimpleName() + "]: Starting at " + LocalDateTime.now());

        EnumSet<ShowStatusEnum> statusToUpdate = EnumSet.of(
                ShowStatusEnum.ARTIST_PROPOSAL,
                ShowStatusEnum.MANAGER_PROPOSAL,
                ShowStatusEnum.NEGOTIATION,
                ShowStatusEnum.ARTIST_ACCEPTED,
                ShowStatusEnum.MANAGER_ACCEPTED,
                ShowStatusEnum.CONFIRMED
        );

        List<Show> shows = showRepository.findAllShowsBeforeDateTimeAndStatusIn(LocalDateTime.now(), statusToUpdate);

        shows.forEach(s -> {
            ShowStatusEnum newStatus = s.getStatus().equals(ShowStatusEnum.CONFIRMED) ? ShowStatusEnum.CONCLUDED : ShowStatusEnum.EXPIRED;
            s.setStatus(newStatus);
        });

        if (!shows.isEmpty()) {
            showRepository.saveAll(shows);
        }

        System.out.println(String.format("%d Shows were updated", shows.size()));
        System.out.println(String.format("["+ this.getClass().getSimpleName() + "] finished at %s", LocalDateTime.now()));

        appEventPublisher.publishEvent(new ShowRecordConsistencyEvent(this));
    }
}
