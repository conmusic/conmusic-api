package school.sptech.conmusicapi.modules.genre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.genre.entities.Genre;

import java.util.Optional;

@Repository
public interface IGenreRepository extends JpaRepository<Genre, Integer> {

    Optional<Genre> findByName(String name);
}
