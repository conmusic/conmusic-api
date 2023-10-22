package school.sptech.conmusicapi.modules.media.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.media.entities.Media;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMediaRepository extends JpaRepository<Media, Integer> {

    int countByUserId(Integer user_id);

    int countByEstablishmentId(Integer establishment_id);

    List<Media> findByUserId(Integer user_id);

    List<Media> findByEstablishmentId(Integer id);
}
