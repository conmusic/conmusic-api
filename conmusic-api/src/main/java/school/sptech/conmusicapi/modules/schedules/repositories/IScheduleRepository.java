package school.sptech.conmusicapi.modules.schedules.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findByEventId(Integer id);

    List<Schedule> findByEventEstablishmentId(Integer id);

    @Query("""
        SELECT s
        FROM Schedule s
        WHERE
            s.recurrence.id IS NULL
            AND s.event.id = :eventId
            AND (s.startDateTime >= :date OR s.endDateTime >= :date)
    """)
    List<Schedule> findDetachedByEventIdAndScheduleAfter(Integer eventId, LocalDate date);
}
