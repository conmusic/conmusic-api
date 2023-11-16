package school.sptech.conmusicapi.modules.show.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.sptech.conmusicapi.modules.show.entities.ShowRecord;
import school.sptech.conmusicapi.shared.utils.statistics.GroupIdCount;

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
            AND (s.startDateTime BETWEEN :startDate AND :endDate
                OR s.dateAction BETWEEN :startDate AND :endDate)
    """)
    List<ShowRecord> findLifeCycleChangesByUserIdBetweenInterval(LocalDateTime startDate, LocalDateTime endDate, Integer userId);

    @Query("""
        SELECT
            new school.sptech.conmusicapi.shared.utils.statistics.GroupIdCount(s.show.artist.id, COUNT(s.id))
        FROM ShowRecord s
        WHERE 
            s.status = 7
            AND (s.startDateTime BETWEEN :startDate AND :endDate
                OR s.dateAction BETWEEN :startDate AND :endDate)
        GROUP BY s.show.artist.id
    """)
    List<GroupIdCount> countShowsConcludedShowsGroupByArtistId(LocalDateTime startDate, LocalDateTime endDate);
}
