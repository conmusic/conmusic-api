package school.sptech.conmusicapi.modules.schedules.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findByEventId(Integer id);

    List<Schedule> findByEventEstablishmentId(Integer id);

    List<Schedule> findByEventIdAndStartDateTimeIsAfterOrEndDateTimeIsAfter(Integer eventId, LocalDate today, LocalDate today1);
}
