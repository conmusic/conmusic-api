package school.sptech.conmusicapi.modules.recurrence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.recurrence.entities.Recurrence;

@Repository
public interface IRecurrenceRepository extends JpaRepository<Recurrence, Integer> {
}
