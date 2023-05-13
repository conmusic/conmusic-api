package school.sptech.conmusicapi.modules.events.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.events.entities.Event;

@Repository
public interface IEventRepository extends JpaRepository<Event, Integer> {
}
