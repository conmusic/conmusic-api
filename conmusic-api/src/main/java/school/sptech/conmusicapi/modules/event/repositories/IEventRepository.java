package school.sptech.conmusicapi.modules.event.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.event.entities.Evento;

import java.util.Optional;

@Repository
public interface IEventRepository extends JpaRepository<Evento, Integer> {
    Optional<Evento> findById(Integer id);
}
