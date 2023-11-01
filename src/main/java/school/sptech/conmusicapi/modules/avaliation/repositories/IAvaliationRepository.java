package school.sptech.conmusicapi.modules.avaliation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.conmusicapi.modules.avaliation.entities.Avaliation;

import java.util.List;

public interface IAvaliationRepository  extends JpaRepository<Avaliation, Integer> {
    List<Avaliation> findByShowId(Integer Id);
    List<Avaliation> findByArtistId(Integer Id);
}
