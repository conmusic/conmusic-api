package school.sptech.conmusicapi.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import school.sptech.conmusicapi.modules.show.entities.Show;
import school.sptech.conmusicapi.modules.show.entities.ShowRecord;
import school.sptech.conmusicapi.modules.show.mapper.ShowRecordMapper;
import school.sptech.conmusicapi.modules.show.repositories.IShowRecordRepository;
import school.sptech.conmusicapi.modules.show.repositories.IShowRepository;
import school.sptech.conmusicapi.modules.show.util.RecordTypeEnum;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;
import school.sptech.conmusicapi.modules.user.entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class ShowRecordConsistencyJob {
    @Autowired
    private IShowRepository showRepository;
    @Autowired
    private IShowRecordRepository showRecordRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        System.out.println("["+ this.getClass().getSimpleName() + "]: Starting Job at " + LocalDateTime.now());
        ShowStatusEnum[] showStatus = ShowStatusEnum.values();

        List<ShowRecord> recordsToInsert = new ArrayList<>();
        int countInsertedRecords = 0;

        for (ShowStatusEnum status : showStatus) {
            List<Show> shows = showRepository.findAllByStatus(status);
            System.out.println(String.format("For status %s were found %d shows", status.name(), shows.size()));

            for (Show show : shows) {
                List<ShowRecord> records = showRecordRepository.findByShowIdAndRecordType(show.getId(), RecordTypeEnum.STATUS);
                List<ShowStatusEnum> missingStatus = getMissingStatusChanges(show.getStatus(), records);

                if (!missingStatus.isEmpty()) {
                    List<ShowRecord> missingRecords = createMissingRecords(show, missingStatus);

                    recordsToInsert.addAll(missingRecords);
                }

                System.out.println(String.format(
                        "For Show with id %d were found %d records, and are missing %d records",
                        show.getId(),
                        records.size(),
                        missingStatus.size()));
            }
        }

        if (!recordsToInsert.isEmpty()) {
            List<ShowRecord> insertedRecords = showRecordRepository.saveAll(recordsToInsert);
            countInsertedRecords = insertedRecords.size();
        }

        System.out.println(String.format("%d Records were missing. We inserted a total of %d records", recordsToInsert.size(), countInsertedRecords));
        System.out.println(String.format("Job finished at %s", LocalDateTime.now()));
    }

    private List<ShowStatusEnum> getMissingStatusChanges(ShowStatusEnum currentStatus, List<ShowRecord> records) {
        List<ShowStatusEnum> missingStatusChanges = new ArrayList<>();

        if (records.stream().noneMatch(r -> r.getStatus().equals(currentStatus))) {
            missingStatusChanges.add(currentStatus);
        }

        switch (currentStatus) {
            case CONCLUDED,
                    ARTIST_CANCELED,
                    MANAGER_CANCELED:
            {
                if (records.stream().noneMatch(r ->
                        r.getStatus().equals(ShowStatusEnum.CONFIRMED))
                ) {
                    missingStatusChanges.add(ShowStatusEnum.CONFIRMED);
                }
            }
            case CONFIRMED: {
                if (records.stream().noneMatch(r ->
                        r.getStatus().equals(ShowStatusEnum.ARTIST_ACCEPTED)
                                || r.getStatus().equals(ShowStatusEnum.MANAGER_ACCEPTED))
                ) {
                    missingStatusChanges.add(randomlySelectBetweenTwoStatus(
                            ShowStatusEnum.ARTIST_ACCEPTED,
                            ShowStatusEnum.MANAGER_ACCEPTED
                    ));
                }
            }
            case ARTIST_ACCEPTED,
                    MANAGER_ACCEPTED,
                    ARTIST_WITHDRAW,
                    MANAGER_WITHDRAW,
                    ARTIST_WITHDRAW_BY_EXCHANGE,
                    MANAGER_WITHDRAW_BY_EXCHANGE:
            {
                if (records.stream().noneMatch(r ->
                        r.getStatus().equals(ShowStatusEnum.NEGOTIATION))
                ) {
                    missingStatusChanges.add(ShowStatusEnum.NEGOTIATION);
                }
            }
            case NEGOTIATION,
                    ARTIST_REJECTED,
                    MANAGER_REJECTED,
                    EXPIRED:
            {
                if (records.stream().noneMatch(r ->
                        r.getStatus().equals(ShowStatusEnum.ARTIST_PROPOSAL)
                        || r.getStatus().equals(ShowStatusEnum.MANAGER_PROPOSAL))
                ) {
                    missingStatusChanges.add(randomlySelectBetweenTwoStatus(
                            ShowStatusEnum.ARTIST_PROPOSAL,
                            ShowStatusEnum.MANAGER_PROPOSAL
                    ));
                }
            } break;
            default: break;
        }

        return missingStatusChanges;
    }

    private List<ShowRecord> createMissingRecords(Show show, List<ShowStatusEnum> missingStatusChanges) {
        List<ShowRecord> showRecord = new ArrayList<>();

        for (ShowStatusEnum missingStatus : missingStatusChanges) {
            User actor = missingStatus.name().contains("ARTIST")
                    ? show.getArtist()
                    : show.getEvent().getEstablishment().getManager();

            LocalDateTime actionDate = getActionDate(show.getSchedule().getStartDateTime(), missingStatus);

            ShowRecord record = ShowRecordMapper.createRecord(show, actor, RecordTypeEnum.STATUS);
            record.setStatus(missingStatus);
            record.setDateAction(actionDate);

            showRecord.add(record);
        }

        return showRecord;
    }

    private ShowStatusEnum randomlySelectBetweenTwoStatus(ShowStatusEnum value1, ShowStatusEnum value2) {
        return ThreadLocalRandom.current().nextInt(1, 11) % 2 == 0
                ? value1
                : value2;
    }

    private LocalDateTime getActionDate(LocalDateTime startDateTime, ShowStatusEnum status) {
        return switch (status) {
            case NEGOTIATION -> startDateTime.minusDays(14);
            case ARTIST_PROPOSAL, MANAGER_PROPOSAL -> startDateTime.minusDays(21);
            case CONFIRMED, MANAGER_WITHDRAW_BY_EXCHANGE, ARTIST_WITHDRAW_BY_EXCHANGE -> startDateTime.minusDays(7);
            case EXPIRED, CONCLUDED -> startDateTime.plusMinutes(30);
            case ARTIST_ACCEPTED, MANAGER_ACCEPTED -> startDateTime.minusDays(17);
            case ARTIST_CANCELED, MANAGER_CANCELED -> startDateTime.minusDays(3);
            case ARTIST_WITHDRAW, MANAGER_WITHDRAW -> startDateTime.minusDays(15);
            case ARTIST_REJECTED, MANAGER_REJECTED ->  startDateTime.minusDays(20);
            case UNDEFINED -> startDateTime;
        };
    }
}
