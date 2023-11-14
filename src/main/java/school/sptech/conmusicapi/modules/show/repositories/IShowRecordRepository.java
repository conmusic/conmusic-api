package school.sptech.conmusicapi.modules.show.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.show.entities.ShowRecord;
import school.sptech.conmusicapi.modules.show.util.ShowStatusEnum;
import school.sptech.conmusicapi.shared.utils.statistics.StatusCount;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;

@Repository
public interface IShowRecordRepository extends JpaRepository<ShowRecord, Integer> {
    List<ShowRecord> findByShowId(Integer id);

    @Query("""
        SELECT 
            new school.sptech.conmusicapi.shared.utils.statistics.StatusCount(s.status, COUNT(s.id))
        FROM ShowRecord s
        WHERE
            s.status IN :status
            AND (s.show.artist.id = :userId OR s.show.event.establishment.manager.id = :userId)
            AND (s.show.schedule.startDateTime BETWEEN :startDate AND :endDate
                OR s.dateAction BETWEEN :startDate AND :endDate
        GROUP BY s.status
    """)
    List<StatusCount> countShowsByStatusInDateInterval(EnumSet<ShowStatusEnum> status, LocalDateTime startDate, LocalDateTime endDate, Integer userId);
}
