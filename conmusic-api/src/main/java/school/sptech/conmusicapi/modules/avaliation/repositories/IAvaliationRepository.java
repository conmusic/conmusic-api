package school.sptech.conmusicapi.modules.avaliation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.conmusicapi.modules.avaliation.entities.Avaliation;

public interface IAvaliationRepository  extends JpaRepository<Avaliation, Integer> {
}
