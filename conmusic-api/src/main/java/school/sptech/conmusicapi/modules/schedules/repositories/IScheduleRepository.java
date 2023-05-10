package school.sptech.conmusicapi.modules.schedules.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.schedules.entities.Schedule;

@Repository
public interface IScheduleRepository extends JpaRepository<Schedule, Integer> {
}
