package school.sptech.conmusicapi.modules.events.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.events.entities.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IEventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByEstablishmentId(Integer id);

    List<Event> findBySchedulesStartDateTimeIsAfterAndSchedulesConfirmedFalse(LocalDateTime date);
}
