package school.sptech.conmusicapi.modules.house.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.house.entities.House;

@Repository
public interface IHouseRepository extends JpaRepository<House, Integer> {
    Boolean existsByCnpj(String cnpj);
}
