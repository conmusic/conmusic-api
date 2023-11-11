package school.sptech.conmusicapi.modules.events.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.events.entities.Event;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IEventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    List<Event> findByEstablishmentId(Integer id);

    List<Event> findBySchedulesStartDateTimeIsAfterAndSchedulesConfirmedFalse(LocalDateTime date);

    List<Event> findByEstablishmentManagerId(Integer id);
}
