package school.sptech.conmusicapi.modules.admin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.admin.entities.Admin;

@Repository
public interface IAdminRepository extends JpaRepository<Admin, Integer> {
}
