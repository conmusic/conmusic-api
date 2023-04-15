package school.sptech.conmusicapi.modules.event.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.event.entities.Event;

import java.util.Optional;

@Repository
public interface IEventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findById(Integer id);
}
