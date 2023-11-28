package school.sptech.conmusicapi.modules.establishment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.establishment.entities.Establishment;

import java.util.List;

@Repository
public interface IEstablishmentRepository extends JpaRepository<Establishment, Integer>, JpaSpecificationExecutor<Establishment> {
    Boolean existsByCnpj(String cnpj);

    List<Establishment> findByManagerId(Integer id);
}
