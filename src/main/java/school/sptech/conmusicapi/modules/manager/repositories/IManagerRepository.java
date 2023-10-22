package school.sptech.conmusicapi.modules.manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.manager.entities.Manager;

@Repository
public interface IManagerRepository extends JpaRepository<Manager, Integer> {
}
