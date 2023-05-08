package school.sptech.conmusicapi.modules.gender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.gender.entities.Gender;

import java.util.Optional;

@Repository
public interface IGenderRepository extends JpaRepository<Gender, Integer> {

    Optional<Gender> findByName(String name);
}
