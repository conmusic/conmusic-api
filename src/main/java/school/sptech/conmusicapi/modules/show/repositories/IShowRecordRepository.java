package school.sptech.conmusicapi.modules.show.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.show.entities.ShowRecord;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IShowRecordRepository extends JpaRepository<ShowRecord, Integer> {
    List<ShowRecord> findByShowId(Integer id);

    @Query("""
        SELECT s
        FROM ShowRecord s
        WHERE
            s.recordType = 2
            AND (s.show.artist.id = :userId OR s.show.event.establishment.manager.id = :userId)
            AND (s.show.schedule.startDateTime BETWEEN :startDate AND :endDate
                OR s.dateAction BETWEEN :startDate AND :endDate)
    """)
    List<ShowRecord> countShowsByStatusInDateInterval(LocalDateTime startDate, LocalDateTime endDate, Integer userId);
}
