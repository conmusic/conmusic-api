package school.sptech.conmusicapi.modules.event.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.artist.entities.Artist;
import school.sptech.conmusicapi.modules.event.entities.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IEventRepository extends JpaRepository<Event, Integer> {
    @Override
    List<Event> findAll();

}
