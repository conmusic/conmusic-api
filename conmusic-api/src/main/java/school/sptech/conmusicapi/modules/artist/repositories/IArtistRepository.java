package school.sptech.conmusicapi.modules.artist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.artist.entities.Artist;

@Repository
public interface IArtistRepository extends JpaRepository<Artist, Integer> {
    Boolean existsByCpf(String cpf);
}
