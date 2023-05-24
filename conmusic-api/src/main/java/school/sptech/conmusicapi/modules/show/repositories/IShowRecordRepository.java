package school.sptech.conmusicapi.modules.show.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.show.entities.ShowRecord;

@Repository
public interface IShowRecordRepository extends JpaRepository<ShowRecord, Integer> {
}
